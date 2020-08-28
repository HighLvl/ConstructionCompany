create or replace function check_new_brigade_member()
    returns trigger as
$$
begin
    if not exists
        (
            select *
            from staff
                     join title
                          on (title_id = title.id)
            where (staff.id = new.staff_id and category_id = 2)
        )
    then
        raise exception 'Бригада должна состоять только из рабочих.';
    end if;
    return new;
end;
$$ language plpgsql;

create trigger check_new_brigade_member_trigger
    before update or insert
    on brigade_members
    for each row
execute procedure check_new_brigade_member();

create or replace function check_and_update_plot_chief_title()
    returns trigger as
$$
begin
    if not exists
        (
            select *
            from staff
                     join title
                          on (title_id = title.id)
            where (staff.id = new.chief_id and category_id = 1)
        )
    then
        raise exception 'Начальник участка должен быть из инженерно-технического персонала.';
    end if;

    if exists
        (
            select  *
            from management
            where new.mng_id = management.id and management.chief_id = new.chief_id
        )
    then
        raise exception 'Сотрудник не может подчиняться самому себе';
    end if;

    /*обновить должность сотрудника на начальника участка и установить начальника управления в качестве начальника сотрудника*/
    update staff
    set title_id = 14,
        chief_id = management.chief_id
    from (select chief_id
          from management
          where management.id = new.mng_id) as management
    where staff.id = new.chief_id;

    return new;
end;
$$ language plpgsql;

create trigger check_and_update_plot_chief_title_trigger
    before update or insert
    on plot
    for each row
execute procedure check_and_update_plot_chief_title();

create or replace function check_and_update_management_chief_title()
    returns trigger as
$$
begin
    if not exists
        (
            select *
            from staff
                     join title
                          on (title_id = title.id)
            where (staff.id = new.chief_id and category_id = 1)
        )
    then
        raise exception 'Начальник управления должен быть из инженерно-технического персонала.';
    end if;

    if exists
        (
            select  *
            from plot
            where plot.mng_id = new.id and plot.chief_id = new.chief_id
        )
    then
        raise exception 'Сотрудник не может подчиняться самому себе';
    end if;

    /*обновить должность сотрудника на начальника управления*/
    update staff
    set title_id = 13,
        chief_id = null
    where staff.id = new.chief_id;

    /*обновить начальников сотрудников, которые являются начальниками участков под руководством начальника new управления*/
    update staff
    set chief_id = new.chief_id
    from (select plot.chief_id
          from plot
          where plot.mng_id = new.id) as plot
    where staff.id = plot.chief_id;

    return new;
end;
$$ language plpgsql;

create trigger check_and_update_management_chief_title_trigger
    before update or insert
    on management
    for each row
execute procedure check_and_update_management_chief_title();
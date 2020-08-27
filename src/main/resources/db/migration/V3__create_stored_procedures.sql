create or replace function compute_time_overrun(start_date date, finish_date date, deadline int)
    returns integer as
$$
select cast(DATE_PART('day', finish_date::timestamp - start_date::timestamp) - deadline as int);
$$ language sql;

create or replace function compute_int_overrun(cons_amount int, est_amount int)
    returns integer as
$$
select cons_amount - est_amount;
$$ language sql;

create or replace view report as
select row_number() over() as id,
       brigade_id,
       object_id,
       work_type_id,
       start_date,
       finish_date,
       deadline,
       compute_time_overrun(start_date, finish_date, deadline)           as time_overrun,
       material_id,
       material_consumption.amount                                       as cons_amount,
       estimate.amount                                                   as est_amount,
       compute_int_overrun(material_consumption.amount, estimate.amount) as mat_cons_overrun
from object_brigade ob
         join work_shedule
              on (work_shedule_id = work_shedule.id)
         join material_consumption
              on (ob.id = material_consumption.id)
         join estimate
              on (estimate.id = estimate_id);
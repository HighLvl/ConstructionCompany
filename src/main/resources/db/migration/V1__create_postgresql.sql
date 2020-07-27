/* ---------------------------------------------------------------------- */
/* Script generated with: DeZign for Databases 11.0.4                     */
/* Target DBMS:           PostgreSQL 11                                   */
/* Project file:          construction_company.dez                        */
/* Project name:                                                          */
/* Author:                                                                */
/* Script type:           Database creation script                        */
/* Created on:            2020-07-23 21:09                                */
/* ---------------------------------------------------------------------- */


/* ---------------------------------------------------------------------- */
/* Add tables                                                             */
/* ---------------------------------------------------------------------- */

/* ---------------------------------------------------------------------- */
/* Add table "title_category"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE title_category (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    CONSTRAINT PK_title_category PRIMARY KEY (id),
    CONSTRAINT TUC_title_category_1 UNIQUE (name)
);

/* ---------------------------------------------------------------------- */
/* Add table "prototype_type"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE prototype_type (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    CONSTRAINT PK_prototype_type PRIMARY KEY (id),
    CONSTRAINT TUC_prototype_type_1 UNIQUE (name)
);

/* ---------------------------------------------------------------------- */
/* Add table "customer"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE customer (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    phone_number TEXT  NOT NULL,
    CONSTRAINT PK_customer PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "work_type"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE work_type (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    CONSTRAINT PK_work_type PRIMARY KEY (id),
    CONSTRAINT TUC_work_type_1 UNIQUE (name)
);

/* ---------------------------------------------------------------------- */
/* Add table "material"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE material (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    CONSTRAINT PK_material PRIMARY KEY (id),
    CONSTRAINT TUC_material_1 UNIQUE (name)
);

/* ---------------------------------------------------------------------- */
/* Add table "machinery_type"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE machinery_type (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    CONSTRAINT PK_machinery_type PRIMARY KEY (id),
    CONSTRAINT TUC_machinery_type_1 UNIQUE (name)
);

/* ---------------------------------------------------------------------- */
/* Add table "machinery_model"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE machinery_model (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    type_id BIGINT  NOT NULL,
    CONSTRAINT PK_machinery_model PRIMARY KEY (id),
    CONSTRAINT TUC_machinery_model_1 UNIQUE (name)
);

/* ---------------------------------------------------------------------- */
/* Add table "title"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE title (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    category_id BIGINT  NOT NULL,
    CONSTRAINT PK_title PRIMARY KEY (id),
    CONSTRAINT TUC_title_1 UNIQUE (name)
);

/* ---------------------------------------------------------------------- */
/* Add table "prototype"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE prototype (
    id BIGSERIAL  NOT NULL,
    type_id BIGINT  NOT NULL,
    deadline INTEGER  NOT NULL,
    CONSTRAINT PK_prototype PRIMARY KEY (id),
    CONSTRAINT TCC_prototype_1 CHECK (deadline >= 1)
);

/* ---------------------------------------------------------------------- */
/* Add table "work_shedule"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE work_shedule (
    id BIGSERIAL  NOT NULL,
    prototype_id BIGINT  NOT NULL,
    work_type_id BIGINT  NOT NULL,
    ord INTEGER  NOT NULL,
    deadline INTEGER  NOT NULL,
    CONSTRAINT PK_work_shedule PRIMARY KEY (id),
    CONSTRAINT TUC_work_shedule_1 UNIQUE (prototype_id, work_type_id),
    CONSTRAINT TCC_work_shedule_2 CHECK (ord >= 1),
    CONSTRAINT TCC_work_shedule_3 CHECK (deadline >= 1)
);

/* ---------------------------------------------------------------------- */
/* Add table "estimate"                                                   */
/* ---------------------------------------------------------------------- */

CREATE TABLE estimate (
    id BIGSERIAL  NOT NULL,
    work_shedule_id BIGINT  NOT NULL,
    material_id BIGINT  NOT NULL,
    amount INTEGER  NOT NULL,
    CONSTRAINT PK_estimate PRIMARY KEY (id),
    CONSTRAINT TUC_estimate_1 UNIQUE (work_shedule_id, material_id),
    CONSTRAINT TCC_estimate_2 CHECK (amount >=1)
);

/* ---------------------------------------------------------------------- */
/* Add table "brigade"                                                    */
/* ---------------------------------------------------------------------- */

CREATE TABLE brigade (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    title_id BIGINT  NOT NULL,
    CONSTRAINT PK_brigade PRIMARY KEY (id),
    CONSTRAINT TUC_brigade_1 UNIQUE (name)
);

/* ---------------------------------------------------------------------- */
/* Add table "staff"                                                      */
/* ---------------------------------------------------------------------- */

CREATE TABLE staff (
    id BIGSERIAL  NOT NULL,
    name CHARACTER VARYING(40)  NOT NULL,
    title_id BIGINT  NOT NULL,
    chief_id BIGINT,
    phone_number TEXT  NOT NULL,
    CONSTRAINT PK_staff PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "management"                                                 */
/* ---------------------------------------------------------------------- */

CREATE TABLE management (
    id BIGSERIAL  NOT NULL,
    chief_id BIGINT,
    CONSTRAINT PK_management PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "machinery"                                                  */
/* ---------------------------------------------------------------------- */

CREATE TABLE machinery (
    id BIGSERIAL  NOT NULL,
    model_id BIGINT  NOT NULL,
    mng_id BIGINT,
    CONSTRAINT PK_machinery PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "brigade_members"                                            */
/* ---------------------------------------------------------------------- */

CREATE TABLE brigade_members (
    id BIGSERIAL  NOT NULL,
    brigade_id BIGINT  NOT NULL,
    staff_id BIGINT  NOT NULL,
    start_date DATE  NOT NULL,
    finish_date DATE,
    is_brigadier BOOLEAN  NOT NULL,
    CONSTRAINT PK_brigade_members PRIMARY KEY (id),
    CONSTRAINT TUC_brigade_members_1 UNIQUE (id),
    CONSTRAINT TCC_brigade_members_2 CHECK (finish_date IS NULL OR finish_date >= start_date)
);

/* ---------------------------------------------------------------------- */
/* Add table "plot"                                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE plot (
    id BIGSERIAL  NOT NULL,
    chief_id BIGINT,
    mng_id BIGINT,
    CONSTRAINT PK_plot PRIMARY KEY (id)
);

/* ---------------------------------------------------------------------- */
/* Add table "build_object"                                               */
/* ---------------------------------------------------------------------- */

CREATE TABLE build_object (
    id BIGSERIAL  NOT NULL,
    prototype_id BIGINT  NOT NULL,
    plot_id BIGINT  NOT NULL,
    customer_id BIGINT  NOT NULL,
    start_date DATE  NOT NULL,
    finish_date DATE,
    CONSTRAINT PK_build_object PRIMARY KEY (id),
    CONSTRAINT TCC_build_object_1 CHECK (finish_date IS NULL OR finish_date > start_date)
);

/* ---------------------------------------------------------------------- */
/* Add table "object_brigade"                                             */
/* ---------------------------------------------------------------------- */

CREATE TABLE object_brigade (
    id BIGSERIAL  NOT NULL,
    brigade_id BIGINT  NOT NULL,
    object_id BIGINT  NOT NULL,
    work_shedule_id BIGINT  NOT NULL,
    start_date DATE  NOT NULL,
    finish_date DATE,
    CONSTRAINT PK_object_brigade PRIMARY KEY (id),
    CONSTRAINT TUC_object_brigade_1 UNIQUE (object_id, work_shedule_id),
    CONSTRAINT TCC_object_brigade_2 CHECK (finish_date IS NULL OR finish_date > start_date),
    CONSTRAINT TUC_object_brigade_3 UNIQUE (brigade_id, object_id, work_shedule_id)
);

/* ---------------------------------------------------------------------- */
/* Add table "material_consumption"                                       */
/* ---------------------------------------------------------------------- */

CREATE TABLE material_consumption (
    id BIGSERIAL  NOT NULL,
    brigade_object_id BIGINT  NOT NULL,
    estimate_id BIGINT  NOT NULL,
    amount INTEGER  NOT NULL,
    CONSTRAINT PK_material_consumption PRIMARY KEY (id),
    CONSTRAINT TUC_material_consumption_1 UNIQUE (brigade_object_id, estimate_id),
    CONSTRAINT TCC_material_consumption_2 CHECK (amount >= 0)
);

/* ---------------------------------------------------------------------- */
/* Add table "object_machinery"                                           */
/* ---------------------------------------------------------------------- */

CREATE TABLE object_machinery (
    id BIGSERIAL  NOT NULL,
    object_id BIGINT  NOT NULL,
    machinery_id BIGINT  NOT NULL,
    start_date DATE  NOT NULL,
    finish_date DATE,
    CONSTRAINT PK_object_machinery PRIMARY KEY (id),
    CONSTRAINT TUC_object_machinery_1 UNIQUE (object_id, machinery_id),
    CONSTRAINT TCC_object_machinery_2 CHECK (finish_date IS NULL OR finish_date > start_date)
);

/* ---------------------------------------------------------------------- */
/* Add foreign key constraints                                            */
/* ---------------------------------------------------------------------- */

ALTER TABLE staff ADD CONSTRAINT staff_staff 
    FOREIGN KEY (chief_id) REFERENCES staff (id) ON DELETE SET NULL;

ALTER TABLE staff ADD CONSTRAINT title_staff 
    FOREIGN KEY (title_id) REFERENCES title (id) ON DELETE CASCADE;

ALTER TABLE title ADD CONSTRAINT title_category_title 
    FOREIGN KEY (category_id) REFERENCES title_category (id) ON DELETE CASCADE;

ALTER TABLE plot ADD CONSTRAINT staff_plot 
    FOREIGN KEY (chief_id) REFERENCES staff (id) ON DELETE SET NULL;

ALTER TABLE plot ADD CONSTRAINT management_plot 
    FOREIGN KEY (mng_id) REFERENCES management (id) ON DELETE SET NULL;

ALTER TABLE management ADD CONSTRAINT staff_management 
    FOREIGN KEY (chief_id) REFERENCES staff (id) ON DELETE SET NULL;

ALTER TABLE build_object ADD CONSTRAINT plot_build_object 
    FOREIGN KEY (plot_id) REFERENCES plot (id) ON DELETE CASCADE;

ALTER TABLE build_object ADD CONSTRAINT customer_build_object 
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE SET NULL;

ALTER TABLE build_object ADD CONSTRAINT prototype_build_object 
    FOREIGN KEY (prototype_id) REFERENCES prototype (id) ON DELETE CASCADE;

ALTER TABLE object_brigade ADD CONSTRAINT build_object_object_brigade 
    FOREIGN KEY (object_id) REFERENCES build_object (id) ON DELETE CASCADE;

ALTER TABLE object_brigade ADD CONSTRAINT work_shedule_object_brigade 
    FOREIGN KEY (work_shedule_id) REFERENCES work_shedule (id) ON DELETE CASCADE;

ALTER TABLE object_brigade ADD CONSTRAINT brigade_object_brigade 
    FOREIGN KEY (brigade_id) REFERENCES brigade (id) ON DELETE CASCADE;

ALTER TABLE prototype ADD CONSTRAINT prototype_type_prototype 
    FOREIGN KEY (type_id) REFERENCES prototype_type (id) ON DELETE CASCADE;

ALTER TABLE work_shedule ADD CONSTRAINT work_type_work_shedule 
    FOREIGN KEY (work_type_id) REFERENCES work_type (id) ON DELETE CASCADE;

ALTER TABLE work_shedule ADD CONSTRAINT prototype_work_shedule 
    FOREIGN KEY (prototype_id) REFERENCES prototype (id) ON DELETE CASCADE;

ALTER TABLE estimate ADD CONSTRAINT material_estimate 
    FOREIGN KEY (material_id) REFERENCES material (id) ON DELETE CASCADE;

ALTER TABLE estimate ADD CONSTRAINT work_shedule_estimate 
    FOREIGN KEY (work_shedule_id) REFERENCES work_shedule (id) ON DELETE CASCADE;

ALTER TABLE material_consumption ADD CONSTRAINT object_brigade_material_consumption 
    FOREIGN KEY (brigade_object_id) REFERENCES object_brigade (id) ON DELETE CASCADE;

ALTER TABLE material_consumption ADD CONSTRAINT estimate_material_consumption 
    FOREIGN KEY (estimate_id) REFERENCES estimate (id) ON DELETE CASCADE;

ALTER TABLE machinery ADD CONSTRAINT management_machinery 
    FOREIGN KEY (mng_id) REFERENCES management (id) ON DELETE SET NULL;

ALTER TABLE machinery ADD CONSTRAINT machinery_model_machinery 
    FOREIGN KEY (model_id) REFERENCES machinery_model (id) ON DELETE CASCADE;

ALTER TABLE object_machinery ADD CONSTRAINT build_object_object_machinery 
    FOREIGN KEY (object_id) REFERENCES build_object (id) ON DELETE CASCADE;

ALTER TABLE object_machinery ADD CONSTRAINT machinery_object_machinery 
    FOREIGN KEY (machinery_id) REFERENCES machinery (id) ON DELETE CASCADE;

ALTER TABLE brigade ADD CONSTRAINT title_brigade 
    FOREIGN KEY (title_id) REFERENCES title (id) ON DELETE CASCADE;

ALTER TABLE brigade_members ADD CONSTRAINT staff_brigade_members 
    FOREIGN KEY (staff_id) REFERENCES staff (id) ON DELETE CASCADE;

ALTER TABLE brigade_members ADD CONSTRAINT brigade_brigade_members 
    FOREIGN KEY (brigade_id) REFERENCES brigade (id);

ALTER TABLE machinery_model ADD CONSTRAINT machinery_type_machinery_model 
    FOREIGN KEY (type_id) REFERENCES machinery_type (id) ON DELETE CASCADE;

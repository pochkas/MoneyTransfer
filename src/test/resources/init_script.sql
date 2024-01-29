CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public."user"
(
    id serial NOT NULL,
    "firstName" character varying COLLATE pg_catalog."default",
    "lastName" character varying COLLATE pg_catalog."default",
    email character varying COLLATE pg_catalog."default",
    username character varying COLLATE pg_catalog."default",
    password character varying COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE public."account"
(
    id serial NOT NULL,
    "accountNumber" bigint,
    balance double precision,
    "userId" bigint NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT "userId_fkey" FOREIGN KEY ("userId")
        REFERENCES public."user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "balanceCheck" CHECK (balance >= 0::double precision)
);

CREATE TABLE public."moneyTransfer"
(
    id serial NOT NULL,
    "fromAccountNumber" bigint,
    "toAccountNumber" bigint,
    amount double precision,
    CONSTRAINT "moneyTransfer_pkey" PRIMARY KEY (id),
    CONSTRAINT "amountCheck" CHECK (amount >= 0::double precision)
);



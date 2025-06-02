--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4 (Ubuntu 16.4-0ubuntu0.24.04.2)
-- Dumped by pg_dump version 16.4 (Ubuntu 16.4-0ubuntu0.24.04.2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: notification; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.notification (
    created_on timestamp(6) with time zone NOT NULL,
    from_user_id bigint,
    id bigint NOT NULL,
    status_id bigint NOT NULL,
    to_user_id bigint NOT NULL,
    identifier character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    type character varying(255) NOT NULL
);


ALTER TABLE public.notification OWNER TO daniel;

--
-- Name: notification_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.notification_seq
    START WITH 1
    INCREMENT BY 5
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notification_seq OWNER TO daniel;

--
-- Name: option; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.option (
    id bigint NOT NULL,
    notification_id bigint NOT NULL,
    link character varying(255) NOT NULL,
    title character varying(255) NOT NULL
);


ALTER TABLE public.option OWNER TO daniel;

--
-- Name: option_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.option_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.option_seq OWNER TO daniel;

--
-- Name: status; Type: TABLE; Schema: public; Owner: daniel
--

CREATE TABLE public.status (
    type smallint NOT NULL,
    created_on timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    CONSTRAINT status_type_check CHECK (((type >= 0) AND (type <= 1)))
);


ALTER TABLE public.status OWNER TO daniel;

--
-- Name: status_seq; Type: SEQUENCE; Schema: public; Owner: daniel
--

CREATE SEQUENCE public.status_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.status_seq OWNER TO daniel;

--
-- Data for Name: notification; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.notification (created_on, from_user_id, id, status_id, to_user_id, identifier, title, type) FROM stdin;
2025-05-20 00:45:26.27483+03	\N	407	1	1	intake 24	Не забудьте принять лекарство Афобазол, назначенное на 2025-05-20T03:38:00.016421Z	medicine
2025-05-20 21:58:06.094941+03	\N	412	1	1	intake 28	Не забудьте принять лекарство Тербинафин-Вертекс, назначенное на 2025-05-21T00:28:00.016823Z	medicine
2025-05-20 22:05:00.553746+03	\N	413	1	1	intake 28 1747784100006	Не забудьте принять лекарство Тербинафин-Вертекс, назначенное на 2025-05-20T23:35:00.006544Z	medicine
2025-05-20 22:06:00.036495+03	\N	414	1	1	intake 28 1747784160000	Не забудьте принять лекарство Тербинафин-Вертекс, назначенное на 2025-05-20T23:36:00.000789Z	medicine
2025-05-20 22:07:00.060343+03	\N	415	1	1	intake 28 1747784220001	Не забудьте принять лекарство Тербинафин-Вертекс, назначенное на 2025-05-20T23:37:00.001141Z	medicine
2025-05-20 22:10:47.824126+03	\N	417	1	1	intake 28 1747784280001	Не забудьте принять лекарство Тербинафин-Вертекс, назначенное на 2025-05-20T23:38:00.001884Z	medicine
2025-05-20 22:10:48.151686+03	\N	418	1	1	intake 28 1747784340000	Не забудьте принять лекарство Тербинафин-Вертекс, назначенное на 2025-05-20T23:39:00.000713Z	medicine
\.


--
-- Data for Name: option; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.option (id, notification_id, link, title) FROM stdin;
\.


--
-- Data for Name: status; Type: TABLE DATA; Schema: public; Owner: daniel
--

COPY public.status (type, created_on, id) FROM stdin;
1	2025-05-20 00:43:15.261639+03	1
0	2025-05-20 00:43:15.348652+03	2
\.


--
-- Name: notification_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.notification_seq', 421, true);


--
-- Name: option_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.option_seq', 1, false);


--
-- Name: status_seq; Type: SEQUENCE SET; Schema: public; Owner: daniel
--

SELECT pg_catalog.setval('public.status_seq', 21, true);


--
-- Name: notification notification_identifier_key; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_identifier_key UNIQUE (identifier);


--
-- Name: notification notification_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (id);


--
-- Name: option option_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.option
    ADD CONSTRAINT option_pkey PRIMARY KEY (id);


--
-- Name: status status_pkey; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.status
    ADD CONSTRAINT status_pkey PRIMARY KEY (id);


--
-- Name: status status_type_key; Type: CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.status
    ADD CONSTRAINT status_type_key UNIQUE (type);


--
-- Name: notificaion_to_user_id_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX notificaion_to_user_id_idx ON public.notification USING btree (to_user_id);


--
-- Name: notificaion_type_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX notificaion_type_idx ON public.notification USING btree (type);


--
-- Name: option_notification_id_idx; Type: INDEX; Schema: public; Owner: daniel
--

CREATE INDEX option_notification_id_idx ON public.option USING btree (notification_id);


--
-- Name: notification fk5tak5s7d8v964omfb11iogqun; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fk5tak5s7d8v964omfb11iogqun FOREIGN KEY (status_id) REFERENCES public.status(id);


--
-- Name: option fkgae0ceic8k96ud2ca3jiich9f; Type: FK CONSTRAINT; Schema: public; Owner: daniel
--

ALTER TABLE ONLY public.option
    ADD CONSTRAINT fkgae0ceic8k96ud2ca3jiich9f FOREIGN KEY (notification_id) REFERENCES public.notification(id);


--
-- PostgreSQL database dump complete
--


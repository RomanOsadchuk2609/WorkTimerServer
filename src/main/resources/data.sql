INSERT INTO public.role (id, description, name)
VALUES (1, 'Admin', 'ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO public.role (id, description, name)
VALUES (2, 'User', 'USER')
ON CONFLICT DO NOTHING;

INSERT INTO public."user"(id, username, first_name, last_name, enabled, password, phone_number)
VALUES (1, 'admin', 'Roman', 'Osadchuk', 'TRUE', '$2a$10$dEhoyTTP7eKJb0QPwgqqt.FFdReDtHX3.VQQj.pjd1CMd/R6E7NQ.',
        '+380501234567')
ON CONFLICT DO NOTHING;

INSERT INTO public.users_roles (user_id, role_id)
SELECT 1 as "user_id", 1 as "role_id"
WHERE NOT EXISTS(
        SELECT user_id FROM public.users_roles WHERE user_id = 1 and role_id = 1
    );

INSERT INTO public.users_roles (user_id, role_id)
SELECT 1 as "user_id", 2 as "role_id"
WHERE NOT EXISTS(
        SELECT user_id FROM public.users_roles WHERE user_id = 1 and role_id = 2
    );

INSERT INTO public.task (id, name, description, user_id)
VALUES (1, 'Time Logging', 'Default task for time logging of all users', NULL)
ON CONFLICT DO NOTHING;
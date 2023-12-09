Delete from requester;
Delete from campaign;
Delete from donation;

INSERT INTO public.requester (id, birthday, email, name) VALUES ('1', '2022-06-02', 'test@test.com', 'test');
INSERT INTO public.campaign (id, campaing_goal, current_ammount, description, requester_id) VALUES ('71c48dac-18d1-4ce8-93ad-f60f511382f3', 23131, 0, 'testre', null);
INSERT INTO public.campaign (id, campaing_goal, current_ammount, description, requester_id) VALUES ('71c48dac-18d1-4ce8-93ad-f60313123122', 2322131, 0, 'testre2', null);


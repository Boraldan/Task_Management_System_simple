
INSERT INTO my_task (task_id, title, description, status, priority, manager_id, created_at, is_active)
VALUES
    ('a1f34c10-b1a1-11ee-be56-0242ac120002', 'Task 1 for Manager 1', 'Description for task 1', 'PENDING', 'HIGH',
     '33333333-3333-3333-3333-333333333333', NOW(), TRUE),
    ('a1f34c11-b1a1-11ee-be56-0242ac120002', 'Task 2 for Manager 1', 'Description for task 2', 'IN_PROGRESS', 'MEDIUM',
     '33333333-3333-3333-3333-333333333333', NOW(), TRUE),
    ('a1f34c12-b1a1-11ee-be56-0242ac120002', 'Task 3 for Manager 1', 'Description for task 3', 'COMPLETED', 'LOW',
     '33333333-3333-3333-3333-333333333333', NOW(), TRUE),
    ('a1f34c13-b1a1-11ee-be56-0242ac120002', 'Task 4 for Manager 1', 'Description for task 4', 'PENDING', 'MEDIUM',
     '33333333-3333-3333-3333-333333333333', NOW(), TRUE),
    ('a1f34c14-b1a1-11ee-be56-0242ac120002', 'Task 5 for Manager 1', 'Description for task 5', 'IN_PROGRESS', 'HIGH',
     '33333333-3333-3333-3333-333333333333', NOW(), TRUE),

    ('b2f45d10-c2a2-11ee-be56-0242ac120002', 'Task 1 for Manager 2', 'Description for task 1', 'PENDING', 'HIGH',
     '44444444-4444-4444-4444-444444444444', NOW(), TRUE),
    ('b2f45d11-c2a2-11ee-be56-0242ac120002', 'Task 2 for Manager 2', 'Description for task 2', 'IN_PROGRESS', 'MEDIUM',
     '44444444-4444-4444-4444-444444444444', NOW(), TRUE),
    ('b2f45d12-c2a2-11ee-be56-0242ac120002', 'Task 3 for Manager 2', 'Description for task 3', 'COMPLETED', 'LOW',
     '44444444-4444-4444-4444-444444444444', NOW(), TRUE),
    ('b2f45d13-c2a2-11ee-be56-0242ac120002', 'Task 4 for Manager 2', 'Description for task 4', 'PENDING', 'MEDIUM',
     '44444444-4444-4444-4444-444444444444', NOW(), TRUE),
    ('b2f45d14-c2a2-11ee-be56-0242ac120002', 'Task 5 for Manager 2', 'Description for task 5', 'IN_PROGRESS', 'HIGH',
     '44444444-4444-4444-4444-444444444444', NOW(), TRUE);



INSERT INTO task_assignees (task_id, user_id)
VALUES
    ('a1f34c10-b1a1-11ee-be56-0242ac120002', '11111111-1111-1111-1111-111111111111'),
    ('a1f34c10-b1a1-11ee-be56-0242ac120002', '22222222-2222-2222-2222-222222222222'),
    ('a1f34c11-b1a1-11ee-be56-0242ac120002', '11111111-1111-1111-1111-111111111111'),
    ('a1f34c12-b1a1-11ee-be56-0242ac120002', '22222222-2222-2222-2222-222222222222'),
    ('a1f34c13-b1a1-11ee-be56-0242ac120002', '11111111-1111-1111-1111-111111111111'),
    ('a1f34c13-b1a1-11ee-be56-0242ac120002', '22222222-2222-2222-2222-222222222222'),
    ('a1f34c14-b1a1-11ee-be56-0242ac120002', '22222222-2222-2222-2222-222222222222'),

    ('b2f45d10-c2a2-11ee-be56-0242ac120002', '11111111-1111-1111-1111-111111111111'),
    ('b2f45d11-c2a2-11ee-be56-0242ac120002', '22222222-2222-2222-2222-222222222222'),
    ('b2f45d12-c2a2-11ee-be56-0242ac120002', '11111111-1111-1111-1111-111111111111'),
    ('b2f45d12-c2a2-11ee-be56-0242ac120002', '22222222-2222-2222-2222-222222222222'),
    ('b2f45d13-c2a2-11ee-be56-0242ac120002', '11111111-1111-1111-1111-111111111111'),
    ('b2f45d14-c2a2-11ee-be56-0242ac120002', '22222222-2222-2222-2222-222222222222');


CREATE TABLE my_task (
                         task_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         title VARCHAR(255),
                         description TEXT,
                         status VARCHAR(50),
                         priority VARCHAR(50),
                         manager_id UUID,
                         created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                         updated_at TIMESTAMP,
                         is_active BOOLEAN
);

CREATE TABLE task_assignees (
                                task_id UUID NOT NULL REFERENCES my_task(task_id) ON DELETE CASCADE,
                                user_id UUID NOT NULL,
                                PRIMARY KEY (task_id, user_id)
);

CREATE TABLE t_comment (
                           comment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           content TEXT,
                           user_id UUID,
                           created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                           task_id UUID NOT NULL REFERENCES my_task(task_id) ON DELETE CASCADE
);

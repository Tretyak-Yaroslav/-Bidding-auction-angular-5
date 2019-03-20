CREATE TABLE tb_afe_questionnaire (
    afe_questionnaire_id BIGINT NOT NULL AUTO_INCREMENT,
    questionnaire        JSON   NOT NULL,

    CONSTRAINT PK__tb_afe_questionnaire$afe_questionnaire_id PRIMARY KEY BTREE (afe_questionnaire_id)
);

INSERT INTO tb_afe_questionnaire (
    questionnaire
) VALUES (
    '{
      "questions": [
        {
          "code": "1",
          "text": "Some question #1",
          "answers": [
            {
              "code": "1",
              "text": "Answer #1 of question #1"
            },
            {
              "code": "2",
              "text": "Answer #2 of question #1"
            },
            {
              "code": "3",
              "text": "Answer #3 of question #1"
            },
            {
              "code": "4",
              "text": "Answer #4 of question #1"
            }
          ]
        },
        {
          "code": "2",
          "text": "Some question #2",
          "answers": [
            {
              "code": "1",
              "text": "Answer #1 of question #2"
            },
            {
              "code": "2",
              "text": "Answer #2 of question #2"
            },
            {
              "code": "3",
              "text": "Answer #3 of question #2"
            },
            {
              "code": "4",
              "text": "Answer #4 of question #2"
            }
          ]
        },
        {
          "code": "3",
          "text": "Some question #3",
          "answers": [
            {
              "code": "1",
              "text": "Answer #1 of question #3"
            },
            {
              "code": "2",
              "text": "Answer #2 of question #3"
            },
            {
              "code": "3",
              "text": "Answer #3 of question #3"
            },
            {
              "code": "4",
              "text": "Answer #4 of question #3"
            }
          ]
        },
        {
          "code": "4",
          "text": "Some question #4",
          "answers": [
            {
              "code": "1",
              "text": "Answer #1 of question #4"
            },
            {
              "code": "2",
              "text": "Answer #2 of question #4"
            },
            {
              "code": "3",
              "text": "Answer #3 of question #4"
            },
            {
              "code": "4",
              "text": "Answer #4 of question #4"
            }
          ]
        },
        {
          "code": "5",
          "text": "Some question #5",
          "answers": [
            {
              "code": "1",
              "text": "Answer #1 of question #5"
            },
            {
              "code": "2",
              "text": "Answer #2 of question #5"
            },
            {
              "code": "3",
              "text": "Answer #3 of question #5"
            },
            {
              "code": "4",
              "text": "Answer #4 of question #5"
            }
          ]
        }
      ]
    }'
)
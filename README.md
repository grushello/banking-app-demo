All of the direct requirements are fulfilled.
No testing, no logging.

The model structure explanation:
User-Account - one to many relation.
Transfer has only 1 account it is assigned to. When performing a transfer, two Transfer objects are created.
One is assigned to sender, another to recipient. The distinction is inside transfer type. One gets TRANSFER_IN type, another TRANNFER_OUT.

In order to create account, first you must create a user with a POST /user request.
There are examples of every possible request in sample_requests.http


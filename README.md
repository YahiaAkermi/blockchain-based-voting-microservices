# CloudSync_microservices
CloudSync Voting Microservices A distributed voting application with two microservices: one for managing voting sessions (using MySQL) and the other for casting votes (using MongoDB). Configurations are dynamically updated via webhooks from a centralized GitHub repo, leveraging RabbitMQ for auto-refresh across hundreds of microservices.

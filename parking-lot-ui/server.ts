import cluster from 'cluster';
import os from 'os';
import express, { Express, Request, Response } from 'express';
import path from 'path';
import { fileURLToPath } from 'url';
import { dirname } from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

if (cluster.isMaster) {
    console.log(`Master ${process.pid} is running`);

    // Fork workers.
    for (let i = 0; i < os.cpus().length; i++) {
        cluster.fork();
    }

    cluster.on('exit', (worker, code, signal) => {
        console.log(`worker ${worker.process.pid} died`);
    });
} else {
    const app: Express = express();

    app.use(express.static(path.join(__dirname, 'build')));

    app.get('*', (req: Request, res: Response) => {
        res.sendFile(path.join(__dirname, 'build', 'index.html'));
    });

    app.listen(3000, () => {
        console.log(`Worker ${process.pid} started`);
    });
}

package middlewares.errorHandlers

import org.http4s.server.middleware.{ErrorAction, ErrorHandling}
import cats.effect.IO
import cats.effect.IOApp
import org.http4s.HttpRoutes

// object LoggingError extends IO:

//     def withErrorLogging(router: HttpRoutes[IO]) = ErrorHandling.Recover.total(
//         ErrorAction.log(
//             router,
//             messageFailureLogAction = (t, msg) => 
//                 IO.println(msg) >>
//                 IO.println(t),                
//             serviceErrorLogAction = (t, msg) => 
//                 IO.println(msg) >>
//                 IO.println(t)   
//         )
//     )
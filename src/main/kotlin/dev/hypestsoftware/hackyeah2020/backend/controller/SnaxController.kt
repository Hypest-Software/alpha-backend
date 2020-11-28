package dev.hypestsoftware.hackyeah2020.backend.controller

import dev.hypestsoftware.hackyeah2020.backend.utils.PUBLIC_API_ENDPOINT_V1
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("$PUBLIC_API_ENDPOINT_V1/snax")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
class SnaxController {
    @GetMapping("/iamnot")
    fun getInfo(): ModelAndView {
        return ModelAndView("redirect" + "https://youtu.be/hqRNro3gif8")
    }
}

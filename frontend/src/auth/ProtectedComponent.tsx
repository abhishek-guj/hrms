import React, { type PropsWithChildren } from 'react'
import { getAuthState } from './ProtectedRoute'
import { roles } from './role.util'



const ProtectedComponent = ({ hasRole, children }: PropsWithChildren<{ hasRole: string[] }>) => {
    const { role } = getAuthState();

    if (hasRole && !hasRole.includes(role)) {
        console.log(hasRole, role, hasRole.includes("Admin"))
        return <></>
    }
    return (
        <>{children}</>
    )
}

export default ProtectedComponent
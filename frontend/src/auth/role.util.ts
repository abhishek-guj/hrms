
const roles = {
    admin: "Admin",
    hr: "Hr",
    manager: "Manager",
    employee: "Employee",
}


const getRole = () => localStorage.getItem("role");
const getId = () => localStorage.getItem("employeeId")


const checkManager = (id) => {
    if (!RoleUtil.isManager) {
        return false
    }
    return id === getId()
}

export const RoleUtil = {
    isAdmin: getRole() === roles.admin,
    isHr: getRole() === roles.hr,
    isManager: getRole() === roles.manager,
    isEmplpoyee: getRole() === roles.employee,
    isThisManager: (id) => checkManager(id),
    isAssigned: (bool) => bool,
    myId: getId()
}
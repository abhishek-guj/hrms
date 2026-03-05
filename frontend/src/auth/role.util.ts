export const roles = {
	admin: "Admin",
	hr: "Hr",
	manager: "Manager",
	employee: "Employee",
};

const getRole = () => {
	const role: string | null = localStorage.getItem("role");
	return role ? JSON.parse(role) : undefined;
};
const getId = () => {
	const employeeId: string | null = localStorage.getItem("employeeId");
	return employeeId ? JSON.parse(employeeId) : undefined;
};

const checkManager = (id) => {
	if (!RoleUtil.isManager) {
		return false;
	}
	return `${id}` === `${getId()}`;
};

export const RoleUtil = {
	isAdmin: getRole() === roles.admin,
	isHr: getRole() === roles.hr,
	isManager: getRole() === roles.manager,
	isEmplpoyee: getRole() === roles.employee,
	isThisManager: (id: string) => checkManager(id),
	isAssigned: (bool: boolean) => bool,
	myId: getId(),
};

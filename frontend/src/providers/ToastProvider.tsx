import { Toaster } from "sonner";
const ToastProvider = () => {
	return <Toaster position="bottom-right" closeButton duration={3000} />;
};

export default ToastProvider;

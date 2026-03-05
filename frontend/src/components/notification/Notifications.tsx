import React, { createContext, useContext, useMemo, type PropsWithChildren } from 'react'
import { Button } from '../ui/button'
import { EyeIcon } from 'lucide-react'
import { useRead } from './notification.quer'




// -------------------------------------------------------
// Learning and implementing compound components in react
// https://github.com/cosdensolutions/code/tree/master/videos/long/compound-components-design-pattern
// -------------------------------------------------------


// defining types
type Notification = {
    id: string
    content: string
}
type NotificationContext = {
    notification: Notification
}
type NotificationProps = PropsWithChildren & {
    notification: Notification;
}



// context
const NotificationContext = createContext<NotificationContext | undefined>(undefined);

// useHook
function useNotificationContext() {
    const context = useContext(NotificationContext);
    if (!context) {
        throw new Error("useNotificationContext must be used within notification components")
    }
    return context;
}



// main component
export default function Notification({ children, notification }: NotificationProps) {
    const data = useMemo(() => ({ notification }), [notification])
    return (
        <NotificationContext.Provider value={data}>
            {children}
        </NotificationContext.Provider>
    )
}



Notification.ReadButton = function NotificationReadButton() {
    const { notification } = useNotificationContext();

    const readNotify = useRead();
    const handleClick = async () => {
        readNotify.mutateAsync({ id: notification?.id });
    };

    return <Button
        onClick={handleClick}
        className="cursor-pointer flex justify-center items-center w-8 h-8 p-2 bg-primary text-white rounded-full"
        asChild
    >
        <EyeIcon />
    </Button>
}


Notification.Content = function NotificationContent({ children }: PropsWithChildren) {
    const { notification } = useNotificationContext();
    return (
        <div className="flex gap-3 px-4 py-3 border-primary font-bold w-max max-w-96 h-fit bg-primary/10 justify-center items-center">
            <div className="w-full">{notification?.content}</div>
            {children}
        </div>
    )
}

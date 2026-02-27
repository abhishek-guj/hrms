import {
  BriefcaseBusiness,
  Gamepad,
  GamepadDirectional,
  Instagram,
  Luggage,
  Network,
  Pen,
  Share2Icon,
} from "lucide-react";
export const navLinksList = {
  navMain: [
    {
      title: "Social",
      url: "#",
      items: [
        {
          icon: <Instagram />,
          title: "Achievements",
          url: "/achievements",
        },
      ],
    },
    {
      title: "Travel",
      url: "#",
      items: [
        {
          icon: <Luggage />,
          title: "Travel Plans",
          url: "/travel/plans",
        },
      ],
    },
    {
      title: "Game",
      url: "#",
      items: [
        {
          icon: <Gamepad />,
          title: "Book Game",
          url: "/games",
        },
        {
          icon: <GamepadDirectional />,
          title: "My Bookings",
          url: "/my-slots",
        },
      ],
    },
    {
      title: "Job",
      url: "#",
      items: [
        {
          icon: <BriefcaseBusiness />,
          title: "View Jobs",
          url: "/jobs",
        },
        {
          icon: <Share2Icon />,
          title: "Referrals",
          url: "/referrals",
        },
      ],
    },
    {
      title: "Organization",
      url: "/org-chart",
      items: [
        {
          icon: <Network />,
          title: "Org Chart",
          url: "/org-chart",
        },
      ],
    },
  ],
};

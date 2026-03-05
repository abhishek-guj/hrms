import { render, screen, waitFor } from "@testing-library/react"
import TestComponent from "../TestComponent"
import { describe, expect, it, vi } from "vitest"
import GameMultiSelect from "../../profile/GameMultiSelect";
import userEvent from '@testing-library/user-event';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { useGetGames } from "../../game/queries/game.queries";






const createTestQueryClient = () => {
    const queryClient = new QueryClient({
        defaultOptions: {
            queries: {
                retry: false,
            },
        },
    });
    return queryClient;
};

const renderWithClient = (ui) => {
    vi.mock('@tanstack/react-query', async (importOriginal) => {
        const actual = await importOriginal<typeof import('@tanstack/react-query')>();
        return {
            ...actual,
            useQuery: vi.fn().mockReturnValue({
                data: [
                    {
                        "gameTypeId": 1,
                        "gameTypeName": "pool",
                        "maxSlotDurationMinutes": 60,
                        "slotSizes": [
                            2,
                            4
                        ],
                        "startTime": "10:00",
                        "endTime": "22:00"
                    },
                    {
                        "gameTypeId": 2,
                        "gameTypeName": "chess",
                        "maxSlotDurationMinutes": 30,
                        "slotSizes": [
                            2
                        ],
                        "startTime": "12:00",
                        "endTime": "18:00"
                    }
                ],
                isLoading: false,
                isError: false,
            }),
        };
    });


    const queryClient = createTestQueryClient();
    const wrapper = ({ children }) => (
        <QueryClientProvider client={queryClient}>
            {children}
        </QueryClientProvider>
    );
    return { ...render(ui, { wrapper }), queryClient };
};


describe('Test Component', () => {
    it('renders correctly', () => {
        render(<TestComponent />);
        expect(screen.getByText('TestComponent')).toBeInTheDocument();
    });
});

// ===============================================



describe("Test GameMultiSelect", () => {
    it("should render select as default", async () => {

        const gameMultiSelect = renderWithClient(<GameMultiSelect />);
        // expect(await gameMultiSelect.findByText(/loading/i)).toBeInTheDocument();


        const combobox = gameMultiSelect.getByRole("combobox")
        expect(combobox).toBeInTheDocument();
        await userEvent.click(combobox)

        // await waitFor(() => {
        // })
        const option = gameMultiSelect.getByText(/chess/i)
        console.log("opppppppppppp", option.innerHTML)
        expect(option).toBeInTheDocument();
        await userEvent.click(option)

        expect(gameMultiSelect).toMatchSnapshot()
    })
})
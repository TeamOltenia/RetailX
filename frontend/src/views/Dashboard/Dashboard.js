import {
  Box,
  Button,
  CircularProgress,
  CircularProgressLabel,
  Flex,
  Grid,
  Icon,
  Progress,
  SimpleGrid,
  Spacer,
  Stack,
  Stat,
  StatHelpText,
  StatLabel,
  StatNumber,
  Table,
  Tbody,
  Text,
  Th,
  Thead,
  Input,
  Tr,
} from "@chakra-ui/react";
// Styles for the circular progressbar
import medusa from "assets/img/cardimgfree.png";
// Custom components
import Card from "components/Card/Card.js";
import CardBody from "components/Card/CardBody.js";
import CardHeader from "components/Card/CardHeader.js";
import BarChart from "components/Charts/BarChart";
import LineChart from "components/Charts/LineChart";
import IconBox from "components/Icons/IconBox";
// Icons
import {
  CartIcon,
  DocumentIcon,
  GlobeIcon,
  RocketIcon,
  StatsIcon,
  WalletIcon,
} from "components/Icons/Icons.js";
import axios from "axios";
import DashboardTableRow from "components/Tables/DashboardTableRow";
import TimelineRow from "components/Tables/TimelineRow";
import React, { useEffect, useState } from "react";
import { AiFillCheckCircle } from "react-icons/ai";
import { BiHappy } from "react-icons/bi";
import { BsArrowRight } from "react-icons/bs";
import {
  IoCheckmarkDoneCircleSharp,
  IoEllipsisHorizontal,
} from "react-icons/io5";
// Data
import {
  barChartDataDashboard,
  barChartOptionsDashboard,
  lineChartDataDashboard,
  lineChartOptionsDashboard,
} from "variables/charts";
import { dashboardTableData, timelineData } from "variables/general";
export default function Dashboard() {
  const [sales, setSales] = useState(null);
  const [db1, setDb1] = useState({ data: null, options: null });
  const [db2, setDb2] = useState({ data: null, options: null });
  const [db3, setDb3] = useState({ data: null, options: null });
  const [metrics, setMetrics] = useState();
  const [stockValues, setStockValues] = useState([]);
  const [anomalies, setAnomalies] = useState([]);

  const [predictions, setPredictions] = useState([]);

  const [inputValues, setInputValues] = useState({});

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(barChartDataDashboard);
    const startDate = e.target.elements.startDate.value;
    const endDate = e.target.elements.endDate.value;
    const productID = e.target.elements.productID.value;

    // Parse the dates
    const startDateTime = new Date(startDate);
    const endDateTime = new Date(endDate);

    // Extract year, start month, and end month
    const year = startDateTime.getFullYear();
    const start_month = startDateTime.getMonth() + 1; // Adding 1 because months are zero-based
    const end_month = endDateTime.getMonth() + 1; // Adding 1 because months are zero-based

    // Now you have the year, start month, and end month
    console.log("Start Year:", year);
    console.log("Start Month:", start_month);
    console.log("End Month:", end_month);
    // Update the inputValues state with the values from the form
    setInputValues({
      startDate,
      endDate,
      productID,
    });
  };
  console.log(metrics);

  const setDashboardValues = (array, count) => {
    if (array.length <= 13) return array;
    const stepSize = array.length / (count - 1);
    const distributedIndices = Array.from({ length: count }, (_, i) =>
      Math.round(i * stepSize)
    );
    return distributedIndices.map((index) => array[index]);
  };

  useEffect(() => {
    const fetchData = async () => {
      const {
        startDate,
        endDate,
        productID,
        year,
        start_month,
        end_month,
      } = inputValues;
      console.log(startDate, endDate, productID);
      if (
        startDate == undefined ||
        endDate == undefined ||
        productID == undefined
      )
        return;
      try {
        const response_sales = await axios.get(
          `http://127.0.0.1:8000/sales/${startDate}/${endDate}/${productID}`
        );
        const response_anomalies = await axios.get(
          `http://127.0.0.1:8000/sales/anomaly/${startDate}/${endDate}/${productID}`
        );
        const response_metrics = await axios.get(
          `http://127.0.0.1:8000/sales/metrics/${startDate}/${endDate}/${productID}`
        );

        const response_predictions = await axios.get(
          `http://127.0.0.1:8080/sales/predictions/${productID}/${year}/${start_month}/${end_month}`
        );

        console.log("date: " + response_metrics.data);
        setMetrics(JSON.parse(response_metrics.data));
        let dSales = setDashboardValues(JSON.parse(response_sales.data), 13);
        let dAnomalies = setDashboardValues(
          JSON.parse(response_anomalies.data),
          13
        );

        let dPredictions = setPredictions(
          JSON.parse(response_predictions.data),
          13
        );

        console.log("fd3" + dPredictions);

        let s_dates = [];
        let a_dates = [];
        let a_sales = [];
        let d_sales = [];

        let p_dates = [];
        let p_sales = [];

        let stock = [];
        let a_stock_diff = [];

        for (let value of dSales) {
          if (value != undefined) {
            const inputDate = new Date(value["Date"]);
            const formattedDate = inputDate.toISOString().split("T")[0];
            s_dates.push(formattedDate);
            d_sales.push(value["Sales"]);
            stock.push(value["EndOfDayStock"]);
          }
        }
        for (let value of dAnomalies) {
          if (value != undefined) {
            const inputDate = new Date(value["Date"]);
            const formattedDate = inputDate.toISOString().split("T")[0];
            a_dates.push(formattedDate);
            a_stock_diff.push(value["StockDiff"]);
          }
        }

        for (let value of dPredictions) {
          if (value != undefined) {
            const inputDate = new Date(value["Date"]);
            const formattedDate = inputDate.toISOString().split("T")[0];
            p_dates.push(formattedDate);
            p_sales.push(value["predictions"]);
          }
        }
        console.log("veecbe");
        console.log(p_sales);

        console.log(a_dates);
        let x = lineChartOptionsDashboard;
        let y = lineChartOptionsDashboard;
        let z = barChartOptionsDashboard;
        // z["xaxis"]["categories"] = s_dates;
        y["xaxis"]["categories"] = a_dates;
        x["xaxis"]["categories"] = s_dates;
        console.log(z);
        setDb1({
          data: [
            {
              name: "Sales",
              data: d_sales,
            },
          ],
          options: x,
        });
        setDb2({
          data: [
            {
              name: "StockDiff",
              data: a_stock_diff,
            },
          ],
          options: y,
        });
        setDb3({
          data: [
            {
              name: "Remaining_Stock",
              data: [330, 250, 110, 300, 490, 350, 270, 130, 425],
            },
          ],
          options: z,
        });
        setDb4({
          data: [
            {
              name: "Predictions",
              data: p_sales,
            },
          ],
          options: y,
        });

        setSales(dSales);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [inputValues]);

  return (
    <Flex flexDirection="column" pt={{ base: "120px", md: "75px" }}>
      {/* <InputFormComp /> */}
      <form onSubmit={handleSubmit}>
        <SimpleGrid columns={{ sm: 1, md: 4, xl: 4 }} spacing="24px">
          {/* Start Date */}
          <Card>
            <CardBody>
              <Flex
                flexDirection="row"
                align="center"
                justify="center"
                w="100%"
              >
                <Stat me="auto">
                  <StatLabel
                    fontSize="sm"
                    color="gray.400"
                    fontWeight="bold"
                    pb="2px"
                  >
                    Start Date
                  </StatLabel>
                  <Flex>
                    <Input
                      name="startDate"
                      placeholder="2009-12-01"
                      size="md"
                      color={"gray.100"}
                    />
                  </Flex>
                </Stat>
              </Flex>
            </CardBody>
          </Card>

          {/* End Date */}
          <Card>
            <CardBody>
              <Flex
                flexDirection="row"
                align="center"
                justify="center"
                w="100%"
              >
                <Stat me="auto">
                  <StatLabel
                    fontSize="sm"
                    color="gray.400"
                    fontWeight="bold"
                    pb="2px"
                  >
                    End Date
                  </StatLabel>
                  <Flex>
                    <Input
                      name="endDate"
                      placeholder="2011-01-30"
                      size="md"
                      color={"gray.100"}
                    />
                  </Flex>
                </Stat>
              </Flex>
            </CardBody>
          </Card>

          {/* Product ID */}
          <Card>
            <CardBody>
              <Flex
                flexDirection="row"
                align="center"
                justify="center"
                w="100%"
              >
                <Stat me="auto">
                  <StatLabel
                    fontSize="sm"
                    color="gray.400"
                    fontWeight="bold"
                    pb="2px"
                  >
                    Product ID
                  </StatLabel>
                  <Flex>
                    <Input
                      name="productID"
                      placeholder="10002"
                      size="md"
                      color={"gray.100"}
                    />
                  </Flex>
                </Stat>
              </Flex>
            </CardBody>
          </Card>

          {/* Submit Button */}
          <Button
            type="submit"
            bg="brand.400"
            mt={"2rem"}
            w="50%" // Adjust the width as needed
            mx="auto" // Centers the button horizontally
            _hover={{
              bg: "brand.600", // Changes the background color on hover to brand.600
            }}
          >
            Submit
          </Button>
        </SimpleGrid>
      </form>
      <br />
      {sales != null && sales.length != 0 ? (
        <div>
          {/*-------------------prima componenta maree ATENTIEEEEEEEEEEEEEEEEEEEEEEE----------------------------*/}
          <SimpleGrid columns={{ sm: 1, md: 2, xl: 4 }} spacing="24px">
            {/* MiniStatistics Card */}
            <Card>
              <CardBody>
                <Flex
                  flexDirection="row"
                  align="center"
                  justify="center"
                  w="100%"
                >
                  <Stat me="auto">
                    <StatLabel
                      fontSize="sm"
                      color="gray.400"
                      fontWeight="bold"
                      pb="2px"
                    >
                      Total Sales
                    </StatLabel>
                    <Flex>
                      <StatNumber fontSize="lg" color="#fff">
                        {metrics["Total Sales"].toFixed(2)}
                      </StatNumber>
                      {/* <StatHelpText
                        alignSelf="flex-end"
                        justifySelf="flex-end"
                        m="0px"
                        color="green.400"
                        fontWeight="bold"
                        ps="3px"
                        fontSize="md"
                      >
                        +55%
                      </StatHelpText> */}
                    </Flex>
                  </Stat>
                  <IconBox as="box" h={"45px"} w={"45px"} bg="brand.200">
                    <WalletIcon h={"24px"} w={"24px"} color="#fff" />
                  </IconBox>
                </Flex>
              </CardBody>
            </Card>
            {/* MiniStatistics Card */}
            <Card minH="83px">
              <CardBody>
                <Flex
                  flexDirection="row"
                  align="center"
                  justify="center"
                  w="100%"
                >
                  <Stat me="auto">
                    <StatLabel
                      fontSize="sm"
                      color="gray.400"
                      fontWeight="bold"
                      pb="2px"
                    >
                      Average Sales
                    </StatLabel>
                    <Flex>
                      <StatNumber fontSize="lg" color="#fff">
                        {metrics["Average Sales"].toFixed(2)}
                      </StatNumber>
                      {/* <StatHelpText
                        alignSelf="flex-end"
                        justifySelf="flex-end"
                        m="0px"
                        color="green.400"
                        fontWeight="bold"
                        ps="3px"
                        fontSize="md"
                      >
                        +5%
                      </StatHelpText> */}
                    </Flex>
                  </Stat>
                  <IconBox as="box" h={"45px"} w={"45px"} bg="brand.200">
                    <GlobeIcon h={"24px"} w={"24px"} color="#fff" />
                  </IconBox>
                </Flex>
              </CardBody>
            </Card>
            {/* MiniStatistics Card */}
            <Card>
              <CardBody>
                <Flex
                  flexDirection="row"
                  align="center"
                  justify="center"
                  w="100%"
                >
                  <Stat>
                    <StatLabel
                      fontSize="sm"
                      color="gray.400"
                      fontWeight="bold"
                      pb="2px"
                    >
                      Total Revenue
                    </StatLabel>
                    <Flex>
                      <StatNumber fontSize="lg" color="#fff">
                        {metrics["Total Revenue"].toFixed(2)}
                      </StatNumber>
                      {/* <StatHelpText
                        alignSelf="flex-end"
                        justifySelf="flex-end"
                        m="0px"
                        color="red.500"
                        fontWeight="bold"
                        ps="3px"
                        fontSize="md"
                      >
                        -14%
                      </StatHelpText> */}
                    </Flex>
                  </Stat>
                  <Spacer />
                  <IconBox as="box" h={"45px"} w={"45px"} bg="brand.200">
                    <DocumentIcon h={"24px"} w={"24px"} color="#fff" />
                  </IconBox>
                </Flex>
              </CardBody>
            </Card>
            {/* MiniStatistics Card */}
            <Card>
              <CardBody>
                <Flex
                  flexDirection="row"
                  align="center"
                  justify="center"
                  w="100%"
                >
                  <Stat me="auto">
                    <StatLabel
                      fontSize="sm"
                      color="gray.400"
                      fontWeight="bold"
                      pb="2px"
                    >
                      Average Revenue per Sale
                    </StatLabel>
                    <Flex>
                      <StatNumber fontSize="lg" color="#fff" fontWeight="bold">
                        {metrics["Average Revenue per Sale"].toFixed(2)}
                      </StatNumber>
                      {/* <StatHelpText
                        alignSelf="flex-end"
                        justifySelf="flex-end"
                        m="0px"
                        color="green.400"
                        fontWeight="bold"
                        ps="3px"
                        fontSize="md"
                      >
                        +8%
                      </StatHelpText> */}
                    </Flex>
                  </Stat>
                  <IconBox as="box" h={"45px"} w={"45px"} bg="brand.200">
                    <CartIcon h={"24px"} w={"24px"} color="#fff" />
                  </IconBox>
                </Flex>
              </CardBody>
            </Card>
          </SimpleGrid>
          <br />
          <Card p="28px 0px 0px 0px">
            <CardHeader mb="20px" ps="22px">
              <Flex direction="column" alignSelf="flex-start">
                <Text fontSize="lg" color="#fff" fontWeight="bold" mb="6px">
                  Sales Overview
                </Text>
                {/* <Text fontSize="md" fontWeight="medium" color="gray.400">
                  <Text as="span" color="green.400" fontWeight="bold">
                    (+5%) more
                  </Text>{" "}
                  in 2021
                </Text> */}
              </Flex>
            </CardHeader>
            <Box w="100%" minH={{ sm: "300px" }}>
              <LineChart
                lineChartData={db1["data"]}
                lineChartOptions={db1["options"]}
              />
            </Box>
            {/* <Flex direction="column" w="100%">
              <Box
                bg="linear-gradient(126.97deg, #060C29 28.26%, rgba(4, 12, 48, 0.5) 91.2%)"
                borderRadius="20px"
                display={{ sm: "flex", md: "block" }}
                justify={{ sm: "center", md: "flex-start" }}
                align={{ sm: "center", md: "flex-start" }}
                minH={{ sm: "180px", md: "220px" }}
                p={{ sm: "0px", md: "22px" }}
              >
                <Text fontSize="lg" color="#fff" fontWeight="bold" mb="6px">
                  Sales Overview
                </Text>
                <BarChart
                  barChartOptions={db3["data"]}
                  barChartData={barChartOptionsDashboard}
                />
              </Box>
            </Flex> */}
          </Card>
          <br />
          <Card p="28px 0px 0px 0px">
            <CardHeader mb="20px" ps="22px">
              <Flex direction="column" alignSelf="flex-start">
                <Text fontSize="lg" color="#fff" fontWeight="bold" mb="6px">
                  Anomalies
                </Text>
                {/* <Text fontSize="md" fontWeight="medium" color="gray.400">
                  <Text as="span" color="green.400" fontWeight="bold">
                    (+5%) more
                  </Text>{" "}
                  in 2021
                </Text> */}
              </Flex>
            </CardHeader>
            <Box w="100%" minH={{ sm: "300px" }}>
              <LineChart
                lineChartData={db2["data"]}
                lineChartOptions={db2["options"]}
              />
            </Box>
          </Card>
          <br />
          <Card p="28px 0px 0px 0px">
            <CardHeader mb="20px" ps="22px">
              <Flex direction="column" alignSelf="flex-start">
                <Text fontSize="lg" color="#fff" fontWeight="bold" mb="6px">
                  Predictions
                </Text>
                {/* <Text fontSize="md" fontWeight="medium" color="gray.400">
                  <Text as="span" color="green.400" fontWeight="bold">
                    (+5%) more
                  </Text>{" "}
                  in 2021
                </Text> */}
              </Flex>
            </CardHeader>
            <Box w="100%" minH={{ sm: "300px" }}>
              <LineChart
                lineChartData={db4["data"]}
                lineChartOptions={db4["options"]}
              />
            </Box>
          </Card>
        </div>
      ) : sales == null ? (
        <Flex flexDirection="row" align="center" justify="center" w="100%">
          <p style={{ color: "#FFF" }}>Please fill in the inputs!</p>
        </Flex>
      ) : (
        <Flex flexDirection="row" align="center" justify="center" w="100%">
          <p style={{ color: "#FFF" }}>Invalid inputs!</p>
        </Flex>
      )}
      {/* <Grid
        templateColumns={{ sm: "1fr", md: "1fr 1fr", "2xl": "2fr 1.2fr 1.5fr" }}
        my="26px"
        gap="18px"
      >
        <Card gridArea={{ md: "2 / 1 / 3 / 2", "2xl": "auto" }}>
          <CardHeader mb="24px">
            <Flex direction="column">
              <Text color="#fff" fontSize="lg" fontWeight="bold" mb="4px">
                Satisfaction Rate
              </Text>
              <Text color="gray.400" fontSize="sm">
                From all projects
              </Text>
            </Flex>
          </CardHeader>
          <Flex direction="column" justify="center" align="center">
            <Box zIndex="-1">
              <CircularProgress
                size={200}
                value={80}
                thickness={6}
                color="#582CFF"
                variant="vision"
                rounded
              >
                <CircularProgressLabel>
                  <IconBox
                    mb="20px"
                    mx="auto"
                    bg="brand.200"
                    borderRadius="50%"
                    w="48px"
                    h="48px"
                  >
                    <Icon as={BiHappy} color="#fff" w="30px" h="30px" />
                  </IconBox>
                </CircularProgressLabel>
              </CircularProgress>
            </Box>
            <Stack
              direction="row"
              spacing={{ sm: "42px", md: "68px" }}
              justify="center"
              maxW={{ sm: "270px", md: "300px", lg: "100%" }}
              mx={{ sm: "auto", md: "0px" }}
              p="18px 22px"
              bg="linear-gradient(126.97deg, rgb(6, 11, 40) 28.26%, rgba(10, 14, 35) 91.2%)"
              borderRadius="20px"
              position="absolute"
              bottom="5%"
            >
              <Text fontSize="xs" color="gray.400">
                0%
              </Text>
              <Flex direction="column" align="center" minW="80px">
                <Text color="#fff" fontSize="28px" fontWeight="bold">
                  95%
                </Text>
                <Text fontSize="xs" color="gray.400">
                  Based on likes
                </Text>
              </Flex>
              <Text fontSize="xs" color="gray.400">
                100%
              </Text>
            </Stack>
          </Flex>
        </Card>
        // Referral Tracking 
        <Card gridArea={{ md: "2 / 2 / 3 / 3", "2xl": "auto" }}>
          <Flex direction="column">
            <Flex justify="space-between" align="center" mb="40px">
              <Text color="#fff" fontSize="lg" fontWeight="bold">
                Referral Tracking
              </Text>
              <Button
                borderRadius="12px"
                w="38px"
                h="38px"
                bg="#22234B"
                _hover="none"
                _active="none"
              >
                <Icon as={IoEllipsisHorizontal} color="#7551FF" />
              </Button>
            </Flex>
            <Flex direction={{ sm: "column", md: "row" }}>
              <Flex
                direction="column"
                me={{ md: "6px", lg: "52px" }}
                mb={{ sm: "16px", md: "0px" }}
              >
                <Flex
                  direction="column"
                  p="22px"
                  pe={{ sm: "22e", md: "8px", lg: "22px" }}
                  minW={{ sm: "220px", md: "140px", lg: "220px" }}
                  bg="linear-gradient(126.97deg, #060C29 28.26%, rgba(4, 12, 48, 0.5) 91.2%)"
                  borderRadius="20px"
                  mb="20px"
                >
                  <Text color="gray.400" fontSize="sm" mb="4px">
                    Invited
                  </Text>
                  <Text color="#fff" fontSize="lg" fontWeight="bold">
                    145 people
                  </Text>
                </Flex>
                <Flex
                  direction="column"
                  p="22px"
                  pe={{ sm: "22px", md: "8px", lg: "22px" }}
                  minW={{ sm: "170px", md: "140px", lg: "170px" }}
                  bg="linear-gradient(126.97deg, #060C29 28.26%, rgba(4, 12, 48, 0.5) 91.2%)"
                  borderRadius="20px"
                >
                  <Text color="gray.400" fontSize="sm" mb="4px">
                    Bonus
                  </Text>
                  <Text color="#fff" fontSize="lg" fontWeight="bold">
                    1,465
                  </Text>
                </Flex>
              </Flex>
              <Box mx={{ sm: "auto", md: "0px" }}>
                <CircularProgress
                  size={
                    window.innerWidth >= 1024
                      ? 200
                      : window.innerWidth >= 768
                      ? 170
                      : 200
                  }
                  value={70}
                  thickness={6}
                  color="#05CD99"
                  variant="vision"
                >
                  <CircularProgressLabel>
                    <Flex direction="column" justify="center" align="center">
                      <Text color="gray.400" fontSize="sm">
                        Safety
                      </Text>
                      <Text
                        color="#fff"
                        fontSize={{ md: "36px", lg: "50px" }}
                        fontWeight="bold"
                        mb="4px"
                      >
                        9.3
                      </Text>
                      <Text color="gray.400" fontSize="sm">
                        Total Score
                      </Text>
                    </Flex>
                  </CircularProgressLabel>
                </CircularProgress>
              </Box>
            </Flex>
          </Flex>
        </Card>
      </Grid> 
    */}
      {/* <Grid
        templateColumns={{ sm: "1fr", lg: "1.7fr 1.3fr" }}
        maxW={{ sm: "100%", md: "100%" }}
        gap="24px"
        mb="24px"
      >
        <Card p="28px 0px 0px 0px">
          <CardHeader mb="20px" ps="22px">
            <Flex direction="column" alignSelf="flex-start">
              <Text fontSize="lg" color="#fff" fontWeight="bold" mb="6px">
                Sales Overview
              </Text>
              <Text fontSize="md" fontWeight="medium" color="gray.400">
                <Text as="span" color="green.400" fontWeight="bold">
                  (+5%) more
                </Text>{" "}
                in 2021
              </Text>
            </Flex>
          </CardHeader>
          <Box w="100%" minH={{ sm: "300px" }}>
            <LineChart
              lineChartData={lineChartDataDashboard}
              lineChartOptions={lineChartOptionsDashboard}
            />
          </Box>
        </Card>
        
        <Card p="16px">
          <CardBody>
            <Flex direction="column" w="100%">
              <Box
                bg="linear-gradient(126.97deg, #060C29 28.26%, rgba(4, 12, 48, 0.5) 91.2%)"
                borderRadius="20px"
                display={{ sm: "flex", md: "block" }}
                justify={{ sm: "center", md: "flex-start" }}
                align={{ sm: "center", md: "flex-start" }}
                minH={{ sm: "180px", md: "220px" }}
                p={{ sm: "0px", md: "22px" }}
              >
                <BarChart
                  barChartOptions={barChartOptionsDashboard}
                  barChartData={barChartDataDashboard}
                />
              </Box>
              <Flex
                direction="column"
                mt="24px"
                mb="36px"
                alignSelf="flex-start"
              >
                <Text fontSize="lg" color="#fff" fontWeight="bold" mb="6px">
                  Active Users
                </Text>
                <Text fontSize="md" fontWeight="medium" color="gray.400">
                  <Text as="span" color="green.400" fontWeight="bold">
                    (+23%)
                  </Text>{" "}
                  than last week
                </Text>
              </Flex>
              <SimpleGrid gap={{ sm: "12px" }} columns={4}>
                <Flex direction="column">
                  <Flex alignItems="center">
                    <IconBox
                      as="box"
                      h={"30px"}
                      w={"30px"}
                      bg="brand.200"
                      me="6px"
                    >
                      <WalletIcon h={"15px"} w={"15px"} color="#fff" />
                    </IconBox>
                    <Text fontSize="sm" color="gray.400">
                      Users
                    </Text>
                  </Flex>
                  <Text
                    fontSize={{ sm: "md", lg: "lg" }}
                    color="#fff"
                    fontWeight="bold"
                    mb="6px"
                    my="6px"
                  >
                    32,984
                  </Text>
                  <Progress
                    colorScheme="brand"
                    bg="#2D2E5F"
                    borderRadius="30px"
                    h="5px"
                    value={20}
                  />
                </Flex>
                <Flex direction="column">
                  <Flex alignItems="center">
                    <IconBox
                      as="box"
                      h={"30px"}
                      w={"30px"}
                      bg="brand.200"
                      me="6px"
                    >
                      <RocketIcon h={"15px"} w={"15px"} color="#fff" />
                    </IconBox>
                    <Text fontSize="sm" color="gray.400">
                      Clicks
                    </Text>
                  </Flex>
                  <Text
                    fontSize={{ sm: "md", lg: "lg" }}
                    color="#fff"
                    fontWeight="bold"
                    mb="6px"
                    my="6px"
                  >
                    2.42m
                  </Text>
                  <Progress
                    colorScheme="brand"
                    bg="#2D2E5F"
                    borderRadius="30px"
                    h="5px"
                    value={90}
                  />
                </Flex>
                <Flex direction="column">
                  <Flex alignItems="center">
                    <IconBox
                      as="box"
                      h={"30px"}
                      w={"30px"}
                      bg="brand.200"
                      me="6px"
                    >
                      <CartIcon h={"15px"} w={"15px"} color="#fff" />
                    </IconBox>
                    <Text fontSize="sm" color="gray.400">
                      Sales
                    </Text>
                  </Flex>
                  <Text
                    fontSize={{ sm: "md", lg: "lg" }}
                    color="#fff"
                    fontWeight="bold"
                    mb="6px"
                    my="6px"
                  >
                    2,400$
                  </Text>
                  <Progress
                    colorScheme="brand"
                    bg="#2D2E5F"
                    borderRadius="30px"
                    h="5px"
                    value={30}
                  />
                </Flex>
                <Flex direction="column">
                  <Flex alignItems="center">
                    <IconBox
                      as="box"
                      h={"30px"}
                      w={"30px"}
                      bg="brand.200"
                      me="6px"
                    >
                      <StatsIcon h={"15px"} w={"15px"} color="#fff" />
                    </IconBox>
                    <Text fontSize="sm" color="gray.400">
                      Items
                    </Text>
                  </Flex>
                  <Text
                    fontSize={{ sm: "md", lg: "lg" }}
                    color="#fff"
                    fontWeight="bold"
                    mb="6px"
                    my="6px"
                  >
                    320
                  </Text>
                  <Progress
                    colorScheme="brand"
                    bg="#2D2E5F"
                    borderRadius="30px"
                    h="5px"
                    value={50}
                  />
                </Flex>
              </SimpleGrid>
            </Flex>
          </CardBody>
        </Card>
      </Grid> */}
      {/* <Grid
        templateColumns={{ sm: "1fr", md: "1fr 1fr", lg: "2fr 1fr" }}
        gap="24px"
      >
        <Card p="16px" overflowX={{ sm: "scroll", xl: "hidden" }}>
          <CardHeader p="12px 0px 28px 0px">
            <Flex direction="column">
              <Text fontSize="lg" color="#fff" fontWeight="bold" pb="8px">
                Projects
              </Text>
              <Flex align="center">
                <Icon
                  as={IoCheckmarkDoneCircleSharp}
                  color="teal.300"
                  w={4}
                  h={4}
                  pe="3px"
                />
                <Text fontSize="sm" color="gray.400" fontWeight="normal">
                  <Text fontWeight="bold" as="span">
                    30 done
                  </Text>{" "}
                  this month.
                </Text>
              </Flex>
            </Flex>
          </CardHeader>
          <Table variant="simple" color="#fff">
            <Thead>
              <Tr my=".8rem" ps="0px">
                <Th
                  ps="0px"
                  color="gray.400"
                  fontFamily="Plus Jakarta Display"
                  borderBottomColor="#56577A"
                >
                  Companies
                </Th>
                <Th
                  color="gray.400"
                  fontFamily="Plus Jakarta Display"
                  borderBottomColor="#56577A"
                >
                  Members
                </Th>
                <Th
                  color="gray.400"
                  fontFamily="Plus Jakarta Display"
                  borderBottomColor="#56577A"
                >
                  Budget
                </Th>
                <Th
                  color="gray.400"
                  fontFamily="Plus Jakarta Display"
                  borderBottomColor="#56577A"
                >
                  Completion
                </Th>
              </Tr>
            </Thead>
            <Tbody>
              {dashboardTableData.map((row, index, arr) => {
                return (
                  <DashboardTableRow
                    name={row.name}
                    logo={row.logo}
                    members={row.members}
                    budget={row.budget}
                    progression={row.progression}
                    lastItem={index === arr.length - 1 ? true : false}
                  />
                );
              })}
            </Tbody>
          </Table>
        </Card>
        <Card>
          <CardHeader mb="32px">
            <Flex direction="column">
              <Text fontSize="lg" color="#fff" fontWeight="bold" mb="6px">
                Orders overview
              </Text>
              <Flex align="center">
                <Icon
                  as={AiFillCheckCircle}
                  color="green.500"
                  w="15px"
                  h="15px"
                  me="5px"
                />
                <Text fontSize="sm" color="gray.400" fontWeight="normal">
                  <Text fontWeight="bold" as="span" color="gray.400">
                    +30%
                  </Text>{" "}
                  this month
                </Text>
              </Flex>
            </Flex>
          </CardHeader>
          <CardBody>
            <Flex direction="column" lineHeight="21px">
              {timelineData.map((row, index, arr) => {
                return (
                  <TimelineRow
                    logo={row.logo}
                    title={row.title}
                    date={row.date}
                    color={row.color}
                    index={index}
                    arrLength={arr.length}
                  />
                );
              })}
            </Flex>
          </CardBody>
        </Card>
      </Grid> */}
    </Flex>
  );
}

import {
  SimpleGrid,
  Card,
  CardBody,
  Flex,
  Stat,
  StatLabel,
  Input,
  Button,
} from "@chakra-ui/react";

const YourFormComponent = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
    // Add your form submission logic here
  };

  return (
    <form onSubmit={handleSubmit}>
      <SimpleGrid columns={{ sm: 1, md: 3, xl: 3 }} spacing="24px">
        {/* Start Date */}
        <Card>
          <CardBody>
            <Flex flexDirection="row" align="center" justify="center" w="100%">
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
                  <Input placeholder="" size="md" color={"gray.100"} />
                </Flex>
              </Stat>
            </Flex>
          </CardBody>
        </Card>

        {/* End Date */}
        <Card>
          <CardBody>
            <Flex flexDirection="row" align="center" justify="center" w="100%">
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
                  <Input placeholder="" size="md" color={"gray.100"} />
                </Flex>
              </Stat>
            </Flex>
          </CardBody>
        </Card>

        {/* Product ID */}
        <Card>
          <CardBody>
            <Flex flexDirection="row" align="center" justify="center" w="100%">
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
                  <Input placeholder="" size="md" color={"gray.100"} />
                </Flex>
              </Stat>
            </Flex>
          </CardBody>
        </Card>

        {/* Submit Button */}
        <Button type="submit" colorScheme="teal" mt={4} w="100%">
          Submit
        </Button>
      </SimpleGrid>
    </form>
  );
};

export default InputFormComp;

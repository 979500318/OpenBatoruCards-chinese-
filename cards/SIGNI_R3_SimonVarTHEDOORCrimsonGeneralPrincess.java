package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;

public final class SIGNI_R3_SimonVarTHEDOORCrimsonGeneralPrincess extends Card {

    public SIGNI_R3_SimonVarTHEDOORCrimsonGeneralPrincess()
    {
        setImageSets("WXDi-P15-048");
        setLinkedImageSets("WXDi-P15-006");

        setOriginalName("紅将姫　シモン・バール//THE DOOR");
        setAltNames("コウショウキシモンバールザドアー Koushouki Shimon Baaru Za Doaa");
        setDescription("jp",
                "=R あなたのトラッシュにある＜解放派＞のシグニ１枚を下に重ねて場に出す\n\n" +
                "@E %R %R：あなたの場に《自由の記憶　解放者リル》がいる場合、対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A %X #C #C #C #C #C：ターン終了時まで、このシグニは【アサシン】か【ダブルクラッシュ】を得る。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Simon Bol//THE DOOR, Crimson Warlord");
        setDescription("en",
                "=R Put this SIGNI onto your field with a <<Liberation Division>> SIGNI from your trash underneath it. \n@E %R %R: If \"Liberator Ril, Memory of Freedom\" is on your field, vanish target SIGNI on your opponent's field with power 12000 or less.\n@A %X #C #C #C #C #C: This SIGNI gains [[Assassin]] or [[Double Crush]] until end of turn." +
                "~#Vanish target SIGNI on your opponent's field with power 10000 or less."
        );
        
        setName("en_fan", "Simón Var//THE DOOR, Crimson General Princess");
        setDescription("en_fan",
                "=R Put this SIGNI onto the field with 1 <<Liberation Faction>> SIGNI from your trash under it\n\n" +
                "@E %R %R: If your LRIG is \"Liberator Ril, Memory of Freedom\", target 1 of your opponent's SIGNI with power 12000 or less, and banish it.\n" +
                "@A %X #C #C #C #C #C: Until end of turn, this SIGNI gains [[Assassin]] or [[Double Crush]]." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

		setName("zh_simplified", "红将姬 西蒙·巴尔//THE DOOR");
        setDescription("zh_simplified", 
                "=R把你的废弃区的<<解放派>>精灵1张在下面重叠出场（在空的精灵区出场）\n" +
                "@E %R %R:你的场上有《自由の記憶　解放者リル》的场合，对战对手的力量12000以下的精灵1只作为对象，将其破坏。\n" +
                "@A %X#C #C #C #C #C:直到回合结束时为止，这只精灵得到[[暗杀]]或[[双重击溃]]。" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withClass(CardSIGNIClass.LIBERATION_FACTION).fromTrash());

            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 2)), this::onEnterEff);

            registerActionAbility(new AbilityCostList(new EnerCost(Cost.colorless(1)), new CoinCost(5)), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("自由の記憶　解放者リル"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
                banish(target);
            }
        }

        private void onActionEff()
        {
            StockAbility attachedStock = playerChoiceAction(ActionHint.ASSASSIN, ActionHint.DOUBLECRUSH) == 1 ? new StockAbilityAssassin() : new StockAbilityDoubleCrush();
            attachAbility(getCardIndex(), attachedStock, ChronoDuration.turnEnd());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
    }
}

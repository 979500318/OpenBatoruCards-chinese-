package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.PutUnderCost;

public final class SIGNI_B3_HanayoTHEDOORNaturalPyroxene extends Card {

    public SIGNI_B3_HanayoTHEDOORNaturalPyroxene()
    {
        setImageSets("WXDi-P15-051", "WXDi-P15-051P");

        setOriginalName("羅輝石　花代//THE DOOR");
        setAltNames("ラキセキハナヨザドアー Rakiseki Hanayo Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを下にカードがあるあなたのシグニ１体につき－1000する。\n" +
                "@E @[手札から＜解放派＞のシグニ１枚をこのシグニの下に置く]@：カードを１枚引く。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$1カードを１枚引く。"
        );

        setName("en", "Hanayo//THE DOOR, Natural Pyroxene");
        setDescription("en",
                "@U: At the beginning of your attack phase, target SIGNI on your opponent's field gets --1000 power for each SIGNI on your field with a card underneath it until end of turn.\n@E @[Put a <<Liberation Division>> SIGNI from your hand under this SIGNI]@: Draw a card." +
                "~#Choose one -- \n$$1Down up to two target SIGNI on your opponent's field. \n$$2Draw a card."
        );
        
        setName("en_fan", "Hanayo//THE DOOR, Natural Pyroxene");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and until end of turn, it gets --1000 power for each SIGNI on your field with a card under it.\n" +
                "@E @[Put 1 <<Liberation Faction>> SIGNI from your hand under this SIGNI]@: Draw 1 card." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "罗辉石 花代//THE DOOR");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量依据有下面的牌的你的精灵的数量，每有1只就-1000。\n" +
                "@E 从手牌把<<解放派>>精灵1张放置到这只精灵的下面:抽1张牌。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些横置。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.GEM);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(new PutUnderCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromHand()), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            if(target != null) gainPower(target, -1000 * new TargetFilter().own().SIGNI().withCardsUnder(CardUnderCategory.UNDER).getValidTargetsCount(), ChronoDuration.turnEnd());
        }

        private void onEnterEff()
        {
            draw(1);
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}

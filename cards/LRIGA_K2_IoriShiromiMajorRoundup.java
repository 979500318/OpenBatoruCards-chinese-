package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_IoriShiromiMajorRoundup extends Card {

    public LRIGA_K2_IoriShiromiMajorRoundup()
    {
        setImageSets("WXDi-CP02-047");

        setOriginalName("銀鏡イオリ[一網打尽]");
        setAltNames("シロミイオリイチモウダジン Shiromi Iori Ichimou Dajin");
        setDescription("jp",
                "@E %X：以下を３回行う。「対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。対戦相手のデッキの上からカードを２枚トラッシュに置く。」" +
                "~{{E：あなたのトラッシュから＜ブルアカ＞のカード１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Shiromi Iori [Major Roundup]");
        setDescription("en",
                "@E %X: Perform the following three times. \"Target SIGNI on your opponent's field gets --5000 power until end of turn. Put the top two cards of your opponent's deck into their trash.\"~{{E: Add target <<Blue Archive>> card from your trash to your hand."
        );
        
        setName("en_fan", "Iori Shiromi [Major Roundup]");
        setDescription("en_fan",
                "@E %X: Do the following 3 times: \"Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power. Put the top 2 cards of your opponent's deck into the trash.\"" +
                "~{{E: Target 1 <<Blue Archive>> card from your trash, and add it to your hand."
        );

		setName("zh_simplified", "银镜伊织[一网打尽]");
        setDescription("zh_simplified", 
                "@E %X:进行以下3次。\n" +
                "@>:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。从对战对手的牌组上面把2张牌放置到废弃区。@@\n" +
                "。（进行的每1次都能重新决定对象）\n" +
                "~{{E:从你的废弃区把<<ブルアカ>>牌1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PREFECT_TEAM);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff1);

            EnterAbility enter2 = registerEnterAbility(this::onEnterEff2);
            enter2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            for(int i=0;i<3;i++)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -5000, ChronoDuration.turnEnd());
                
                millDeck(getOpponent(), 2);
            }
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash()).get();
            addToHand(target);
        }
    }
}


package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_HanareMillionDagger extends Card {

    public LRIGA_K2_HanareMillionDagger()
    {
        setImageSets("WXDi-P15-045");

        setOriginalName("ハナレ//ミリオンダガー");
        setAltNames("ハナレミリオンダガー Hanare Mirion Dagaa");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。\n" +
                "@E %X：あなたのトラッシュからあなたのセンタールリグと共通する色を持つシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Hanare//Million Daggers");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --8000 power until end of turn.\n@E %X: Add target SIGNI that shares a color with your Center LRIG from your trash to your hand."
        );
        
        setName("en_fan", "Hanare//Million Dagger");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "@E %X: Target 1 SIGNI that shares a common color with your center LRIG from your trash, and add it to your hand."
        );

		setName("zh_simplified", "离//百万短刃");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "@E %X:从你的废弃区把持有与你的核心分身共通颜色的精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.HANARE);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromTrash()).get();
            addToHand(target);
        }
    }
}

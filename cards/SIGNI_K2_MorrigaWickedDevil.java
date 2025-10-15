package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_MorrigaWickedDevil extends Card {

    public SIGNI_K2_MorrigaWickedDevil()
    {
        setImageSets("WX24-P1-082");

        setOriginalName("凶魔　モリガ");
        setAltNames("キョウマモリガ Kyouma Moriga");
        setDescription("jp",
                "@E %K @[他の＜悪魔＞のシグニ１体を場からトラッシュに置く]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );

        setName("en", "Morriga, Wicked Devil");
        setDescription("en",
                "@E %K @[Put 1 of your other <<Devil>> SIGNI into the trash]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );

		setName("zh_simplified", "凶魔 摩莉甘");
        setDescription("zh_simplified", 
                "@E %K其他的<<悪魔>>精灵1只从场上放置到废弃区:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.BLACK, 1)),
                new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.DEVIL).except(cardId))
            ), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}

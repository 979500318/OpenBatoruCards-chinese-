package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;

public final class ARTS_K_DoublePiton extends Card {

    public ARTS_K_DoublePiton()
    {
        setImageSets("WX25-P2-044");

        setOriginalName("ダブル・ハーケン");
        setAltNames("ダブルハーケン Daburu Haaken");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、ターン終了時まで、このシグニのパワーを－20000する。@@を得る。あなたのトラッシュからあなたのセンタールリグと共通する色を持つシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Double Piton");
        setDescription("en",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, until end of turn, this SIGNI gets --20000 power.@@" +
                "Target 1 SIGNI that shares a common color with your center LRIG from your trash, and add it to your hand."
        );

        setName("zh_simplified", "双重·镰刃");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其得到" +
                "@>@U :当这只精灵攻击时，直到回合结束时为止，这只精灵的力量-20000。@@" +
                "。从你的废弃区把持有与你的核心分身共通颜色的精灵1张作为对象，将其加入手牌。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            CardIndex target1 = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            CardIndex target2 = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromTrash()).get();
            
            if(target1 != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(target1, attachedAuto, ChronoDuration.turnEnd());
            }
            
            addToHand(target2);
        }
        private void onAttachedAutoEff()
        {
            CardIndex source = getAbility().getSourceCardIndex();
            source.getIndexedInstance().gainPower(source, -20000, ChronoDuration.turnEnd());
        }
    }
}

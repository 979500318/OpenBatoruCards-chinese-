package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SPELL_B_BraveStinger extends Card {

    public SPELL_B_BraveStinger()
    {
        setImageSets("WX25-P2-089");

        setOriginalName("ブレイブ・スティンガー");
        setAltNames("ブレイブスティンガー Bureibu Sutingaa");
        setDescription("jp",
                "あなたの＜武勇＞のシグニ１体を対象とし、ターン終了時まで、それは[[アサシン（凍結状態のパワー8000以下のシグニ）]]を得る。" +
                "~#：対戦相手のシグニを２体まで対象とし、それらをダウンする。"
        );

        setName("en", "Brave Stinger");
        setDescription("en",
                "Target 1 of your <<Brave>> SIGNI, and until end of turn, it gains [[Assassin (frozen SIGNI with power 8000 or less)]]." +
                "~#Target up to 2 of your opponent's SIGNI, and down them."
        );

		setName("zh_simplified", "勇猛·毒刺");
        setDescription("zh_simplified", 
                "你的<<武勇>>精灵1只作为对象，直到回合结束时为止，其得到[[暗杀（冻结状态的力量8000以下的精灵）]]。" +
                "~#对战对手的精灵2只最多作为对象，将这些#D。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VALOR)));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            if(target != null)
            {
                attachAbility(target, new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().isState(CardStateFlag.FROZEN) &&
                   cardIndexOpposite.getIndexedInstance().getPower().getValue() <= 8000 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
        }
    }
}

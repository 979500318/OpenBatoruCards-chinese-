package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class SPELL_G_Pierce extends Card {

    public SPELL_G_Pierce()
    {
        setImageSets("WXK01-063");

        setOriginalName("貫穿");
        setAltNames("カンセン Kansen");
        setDescription("jp",
                "あなたの緑のシグニ１体を対象とし、ターン終了時まで、それのパワーを＋2000し、それは【Ｓランサー】を得る。"
        );

        setName("en", "Pierce");
        setDescription("en",
                "Target 1 of your green SIGNI, and until end of turn, it gets +2000 power, and it gains [[S Lancer]]."
        );

		setName("zh_simplified", "贯穿");
        setDescription("zh_simplified", 
                "你的绿色的精灵1只作为对象，直到回合结束时为止，其的力量+2000，其得到[[S枪兵]]。\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));

        setPlayFormat(PlayFormat.KEY);
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
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.GREEN)));
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(), 2000, ChronoDuration.turnEnd());
            attachAbility(spell.getTarget(), new StockAbilitySLancer(), ChronoDuration.turnEnd());
        }
    }
}

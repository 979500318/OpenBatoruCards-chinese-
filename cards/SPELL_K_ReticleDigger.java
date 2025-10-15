package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_K_ReticleDigger extends Card {
    
    public SPELL_K_ReticleDigger()
    {
        setImageSets("WXDi-P03-089");
        
        setOriginalName("レティクル・ディガー");
        setAltNames("レティクルディガー Retikuru Digaa");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－5000する。追加でエクシード４を支払っていた場合、代わりにターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Reticle Digger");
        setDescription("en",
                "As you use this spell, you may pay Exceed 4 as an additional use cost. \n\n" +
                "Target SIGNI on your opponent's field gets --5000 power until end of turn. If you paid the Exceed 4, it gets --12000 power until end of turn instead."
        );
        
        setName("en_fan", "Reticle Digger");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --5000 power. If you additionally paid @[Exceed 4]@, instead until of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "光刻·挖掘");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把@[超越 4]@支付。（从你的分身的下面把牌合计4张放置到分身废弃区）\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-5000。追加把@[超越 4]@支付过的场合，作为替代，直到回合结束时为止，其的力量-12000。\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            spell.setAdditionalCost(new ExceedCost(4));
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(), spell.hasPaidAdditionalCost() ? -12000 : -5000, ChronoDuration.turnEnd());
        }
    }
}

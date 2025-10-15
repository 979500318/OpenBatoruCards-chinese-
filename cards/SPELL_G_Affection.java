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
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_G_Affection extends Card {
    
    public SPELL_G_Affection()
    {
        setImageSets("WXDi-P03-080");
        
        setOriginalName("愛情");
        setAltNames("アイジョウ Aijou");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード４を支払ってもよい。\n\n" +
                "あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋5000し、それは@>@U：このシグニがアタックしたとき、[[エナチャージ１]]をする。@@を得る。\n" +
                "追加でエクシード４を支払っていた場合、代わりにターン終了時まで、あなたのすべてのシグニのパワーを＋5000し、それらは@>@U：このシグニがアタックしたとき、[[エナチャージ１]]をする。@@を得る。"
        );
        
        setName("en", "Affection");
        setDescription("en",
                "As you use this spell, you may pay Exceed 4 as an additional use cost.\n\n" +
                "Target SIGNI on your field gets +5000 power and@>@U: Whenever this SIGNI attacks, [[Ener Charge 1]].@@until end of turn. If you paid the Exceed 4, instead all SIGNI on your field get +5000 power and@>@U: Whenever this SIGNI attacks, [[Ener Charge 1]].@@until end of turn."
        );
        
        setName("en_fan", "Affection");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 4]@ for its use cost.\n\n" +
                "Target 1 of your SIGNI, and until end of turn, it gets +5000 power, and:" +
                "@>@U: Whenever this SIGNI attacks, [[Ener Charge 1]].@@" +
                "If you additionally paid @[Exceed 4]@, instead until end of turn, all of your SIGNI get +5000 power, and:" +
                "@>@U: Whenever this SIGNI attacks, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "爱情");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把@[超越 4]@支付。（从你的分身的下面把牌合计4张放置到分身废弃区）\n" +
                "你的精灵1只作为对象，直到回合结束时为止，其的力量+5000，其得到\n" +
                "@>@U :当这只精灵攻击时，[[能量填充1]]。@@\n" +
                "。追加把@[超越 4]@支付过的场合，作为替代，直到回合结束时为止，你的全部的精灵的力量+5000，这些得到\n" +
                "@>@U :当这只精灵攻击时，[[能量填充1]]。@@\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        
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
            spell.setTargets(spell.hasPaidAdditionalCost() ? null : playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()));
        }
        private void onSpellEff()
        {
            if(spell.hasPaidAdditionalCost())
            {
                gainPower(getSIGNIOnField(getOwner()), 5000, ChronoDuration.turnEnd());
                forEachSIGNIOnField(getOwner(), cardIndex -> {
                    attachAbility(cardIndex, newAttachedAutoAbility(), ChronoDuration.turnEnd());
                });
            } else {
                gainPower(spell.getTarget(), 5000, ChronoDuration.turnEnd());
                attachAbility(spell.getTarget(), newAttachedAutoAbility(), ChronoDuration.turnEnd());
            }
        }
        private AutoAbility newAttachedAutoAbility()
        {
            return new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().enerCharge(1);
        }
    }
}

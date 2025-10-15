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
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_ResonanceOfOptimism extends Card {
    
    public SPELL_R_ResonanceOfOptimism()
    {
        setImageSets("WXDi-P08-061", "SPDi37-03");
        setLinkedImageSets("WXDi-P08-040","WXDi-P08-051","WXDi-P08-065");
        
        setOriginalName("楽天の共鳴");
        setAltNames("ラクテンのキョウメイ Rakuten no Kyoumei");
        setDescription("jp",
                "あなたの《紅天姫　ヒラナ//メモリア》か《幻水　アキノ//メモリア》か《羅石　レイ//メモリア》か赤のシグニ１体を対象とし、ターン終了時まで、それは@>@U $T1：このシグニがアタックしたとき、対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。"
        );
        
        setName("en", "Arcadian Resonance");
        setDescription("en",
                "Target \"Hirana//Memoria, Crimson Angel Queen\", \"Akino//Memoria, Phantom Aquatic Beast\", \"Rei//Memoria, Natural Crystal\", or red SIGNI on your field gains@>@U $T1: When this SIGNI attacks, vanish target SIGNI on your opponent's field with power 5000 or less.@@until end of turn."
        );
        
        setName("en_fan", "Resonance of Optimism");
        setDescription("en_fan",
                "Target 1 of your \"Hirana//Memoria, Crimson Angel Princess\", \"Akino//Memoria, Water Phantom\", \"Rei//Memoria, Natural Stone\", or 1 of your red SIGNI, and until end of turn, it gains:" +
                "@>@U $T1: When this SIGNI attacks, target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );
        
		setName("zh_simplified", "乐天的共鸣");
        setDescription("zh_simplified", 
                "你的《紅天姫　ヒラナ//メモリア》或《幻水　アキノ//メモリア》或《羅石　レイ//メモリア》或红色的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只精灵攻击时，对战对手的力量5000以下的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        
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
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().
                             or(new TargetFilter().withName("紅天姫　ヒラナ//メモリア","幻水　アキノ//メモリア","羅石　レイ//メモリア"), new TargetFilter().withColor(CardColor.RED))));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = getAbility().getSourceCardIndex().getIndexedInstance().playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            getAbility().getSourceCardIndex().getIndexedInstance().banish(target);
        }
    }
}

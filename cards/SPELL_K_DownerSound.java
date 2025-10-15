package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_DownerSound extends Card {
    
    public SPELL_K_DownerSound()
    {
        setImageSets("WXDi-D06-021");
        
        setOriginalName("ダウナー・サウンド");
        setAltNames("ダウナーサウンド Daunaa Saundo");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－6000する。あなたの場に＜DIAGRAM＞のルリグが３体いる場合、代わりにターン終了時まで、それのパワーを－8000する。（パワーが０以下のシグニはルールによってバニッシュされる。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );
        
        setName("en", "Enervating Melody");
        setDescription("en",
                "Target SIGNI on your opponent's field gets --6000 power until end of turn. If you have three <<DIAGRAM>> LRIG on your field, it gets --8000 power until end of turn instead." +
                "~#Target upped SIGNI on your opponent's field gets --15000 power until end of turn."
        );
        
        setName("en_fan", "Downer Sound");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --6000 power. If there are 3 <<DIAGRAM>> LRIGs on your field, instead until end of turn, it gets --8000 power." +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );
        
		setName("zh_simplified", "低沉·声波");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-6000。你的场上的<<DIAGRAM>>分身在3只的场合，作为替代，直到回合结束时为止，其的力量-8000。" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            gainPower(spell.getTarget(), isLRIGTeam(CardLRIGTeam.DIAGRAM) ? -8000 : -6000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(target, -15000, ChronoDuration.turnEnd());
        }
    }
}

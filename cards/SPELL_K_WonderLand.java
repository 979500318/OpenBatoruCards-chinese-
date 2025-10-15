package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_WonderLand extends Card {
    
    public SPELL_K_WonderLand()
    {
        setImageSets("WXDi-D02-29");
        
        setOriginalName("ワンダー・ランド");
        setAltNames("ワンダーランド Wandā Rando");
        setDescription("jp",
                "あなたのエナゾーンから緑のシグニ１枚を対象とし、それを手札に加える。あなたの場に＜アンシエント・サプライズ＞のルリグが３体いる場合、代わりにあなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。"
        );
        
        setName("en", "In Wonderland");
        setDescription("en",
                "Add target black SIGNI in your trash to your hand. If you have three <<Sanbaka>> LRIG on your field, instead add target white or black SIGNI from your trash to your hand." +
                "~#Target SIGNI on your opponent's field gets --8000 power until end of turn."
        );
        
        setName("en_fan", "Wonder Land");
        setDescription("en_fan",
                "Target 1 black SIGNI from your trash, and add it to your hand. If you have 3 <<Sanbaka>> LRIGs on your field, instead target 1 white or black SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power."
        );
        
		setName("zh_simplified", "梦游·仙境");
        setDescription("zh_simplified", 
                "从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。你的场上的<<さんばか>>分身在3只的场合，作为替代，从你的废弃区把白色或黑色的精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。（力量在0以下的精灵因为规则被破坏）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            TargetFilter filter = new TargetFilter(TargetHint.HAND).own().SIGNI();
            filter = isLRIGTeam(CardLRIGTeam.SANBAKA) ? filter.withColor(CardColor.WHITE,CardColor.BLACK) : filter.withColor(CardColor.BLACK);
            
            spell.setTargets(playerTargetCard(filter.fromTrash()));
        }
        private void onSpellEff()
        {
            addToHand(spell.getTarget());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -8000, ChronoDuration.turnEnd());
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_BlackPack extends Card {
    
    public SPELL_K_BlackPack()
    {
        setImageSets("WXDi-P04-086");
        
        setOriginalName("ブラック・パック");
        setAltNames("ブラックパック Burakku Pakku");
        setDescription("jp",
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－10000する。あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋10000する。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2[[エナチャージ２]]"
        );
        
        setName("en", "Black Pack");
        setDescription("en",
                "Target SIGNI on your opponent's field gets --10000 power until end of turn. Target SIGNI on your field gets +10000 power until end of turn." +
                "~#Choose one -- \n$$1 Vanish target level one SIGNI on your opponent's field. \n$$2 [[Ener Charge 2]]."
        );
        
        setName("en_fan", "Black Pack");
        setDescription("en_fan",
                "Target 1 of your opponent's SIGNI, and until end of turn, it gets --10000 power. Target 1 of your SIGNI, and until end of turn, it gets +10000 power." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's level 1 SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 2]]."
        );
        
		setName("zh_simplified", "漆黑·背包");
        setDescription("zh_simplified", 
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-10000。你的精灵1只作为对象，直到回合结束时为止，其的力量+10000。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的等级1的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充2]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.color(CardColor.GREEN, 1));
        
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
            gainPower(spell.getTarget(), -10000, ChronoDuration.turnEnd());
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 10000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
                banish(target);
            } else {
                enerCharge(2);
            }
        }
    }
}

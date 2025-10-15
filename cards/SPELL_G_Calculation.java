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

import java.util.stream.IntStream;

public final class SPELL_G_Calculation extends Card {
    
    public SPELL_G_Calculation()
    {
        setImageSets("WXDi-P07-086");
        
        setOriginalName("演算");
        setAltNames("エンザン Enzan");
        setDescription("jp",
                "シグニ１体を対象とする。２～２０の数字１つを宣言し、ターン終了時まで、それの基本パワーを「この方法で宣言した数字×1000」にする。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Calculation");
        setDescription("en",
                "Declare a number from two to twenty. Target SIGNI's base power becomes the number declared this way multiplied by 1000 until end of turn." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Calculation");
        setDescription("en_fan",
                "Target 1 SIGNI, and declare a number from 2 to 20. Until end of turn, that SIGNI's base power becomes \"the declared number × 1000\"." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );
        
		setName("zh_simplified", "演算");
        setDescription("zh_simplified", 
                "精灵1只作为对象。2~20的数字1种宣言，直到回合结束时为止，其的基本力量变为\n" +
                "@>:这个方法宣言数字x1000@@" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final int[] NUMBERS = IntStream.rangeClosed(2,20).toArray();
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.PLUS).SIGNI()));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                int chosenNumber = playerChoiceNumber(NUMBERS)+1;
                setBasePower(spell.getTarget(), chosenNumber*1000, ChronoDuration.turnEnd());
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}

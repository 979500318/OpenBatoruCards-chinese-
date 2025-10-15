package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_R2_CodeArtSTove extends Card {
    
    public SIGNI_R2_CodeArtSTove()
    {
        setImageSets("WXDi-P02-060");
        
        setOriginalName("コードアート　Ｓトーブ");
        setAltNames("コードアートエストーブ Koodo Aato Esu Toobu");
        setDescription("jp",
                "@C：あなたのトラッシュにスペルがあるかぎり、このシグニのパワーは＋3000される。\n" +
                "@E %R %R %R：あなたのトラッシュにスペルが５枚以上ある場合、ターン終了時まで、このシグニは[[アサシン]]を得る。"
        );
        
        setName("en", "S - Tove, Code: Art");
        setDescription("en",
                "@C: As long as there is a spell in your trash, this SIGNI gets +3000 power.\n" +
                "@E %R %R %R: If there are five or more spells in your trash, this SIGNI gains [[Assassin]] until end of turn."
        );
        
        setName("en_fan", "Code Art S Tove");
        setDescription("en_fan",
                "@C: As long as there is a spell in your trash, this SIGNI gets +3000 power.\n" +
                "@E %R %R %R: If there are 5 or more spells in your trash, until end of turn, this SIGNI gains [[Assassin]]."
        );
        
		setName("zh_simplified", "必杀代号 火炉");
        setDescription("zh_simplified", 
                "@C :你的废弃区有魔法时，这只精灵的力量+3000。\n" +
                "@E %R %R %R:你的废弃区的魔法在5张以上的场合，直到回合结束时为止，这只精灵得到[[暗杀]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(2);
        setPower(7000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.RED, 3)), this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().spell().fromTrash().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            if(new TargetFilter().own().spell().fromTrash().getValidTargetsCount() >= 5)
            {
                attachAbility(getCardIndex(), new StockAbilityAssassin(), ChronoDuration.turnEnd());
            }
        }
    }
}

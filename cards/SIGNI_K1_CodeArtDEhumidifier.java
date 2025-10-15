package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K1_CodeArtDEhumidifier extends Card {
    
    public SIGNI_K1_CodeArtDEhumidifier()
    {
        setImageSets("WXDi-P06-080");
        
        setOriginalName("コードアート　Jヨシツキ");
        setAltNames("コードアートジェイヨシツキ Koodo Aato Jei Yoshitsuki");
        setDescription("jp",
                "@C：あなたのトラッシュにスペルがあるかぎり、このシグニのパワーは＋4000される。" +
                "~#：あなたのトラッシュから#Gを持たないレベル２以下のシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "D - Humidifier, Code: Art");
        setDescription("en",
                "@C: As long as there is a spell in your trash, this SIGNI gets +4000 power." +
                "~#Add target level two or less SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Code Art D Ehumidifier");
        setDescription("en_fan",
                "@C: As long as there is a spell in your trash, this SIGNI gets +4000 power." +
                "~#Target 1 level 2 or lower SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "必杀代号 除湿器");
        setDescription("zh_simplified", 
                "@C :你的废弃区有魔法时，这只精灵的力量+4000。" +
                "~#从你的废弃区把不持有#G的等级2以下的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().spell().fromTrash().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().withLevel(0,2).not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class SIGNI_G2_SitriVerdantDevil extends Card {
    
    public SIGNI_G2_SitriVerdantDevil()
    {
        setImageSets("WXDi-P02-075");
        
        setOriginalName("翠魔　シトリー");
        setAltNames("スイマシトリー Suima Shitorii");
        setDescription("jp",
                "~#：[[エナチャージ１]]をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Sitri, Jade Evil");
        setDescription("en",
                "~#[[Ener Charge 1]]. Then, add up to one target SIGNI from your Ener Zone to your hand or put it onto your field."
        );
        
        setName("en_fan", "Sitri, Verdant Devil");
        setDescription("en_fan",
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "翠魔 西迪");
        setDescription("zh_simplified", 
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromEner()).get();
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

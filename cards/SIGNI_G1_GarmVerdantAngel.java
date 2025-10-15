package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_GarmVerdantAngel extends Card {
    
    public SIGNI_G1_GarmVerdantAngel()
    {
        setImageSets("WXDi-P01-069");
        
        setOriginalName("翠天　ガルム");
        setAltNames("スイテンガルム Suiten Garumu");
        setDescription("jp",
                "@C：あなたのエナゾーンに＜天使＞のシグニが３枚以上あるかぎり、このシグニのパワーは＋4000される。" +
                "~#：[[エナチャージ２]]をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。"
        );
        
        setName("en", "Garmr, Jade Angel");
        setDescription("en",
                "@C: As long as there are three or more <<Angel>> SIGNI in your Ener Zone, this SIGNI gets +4000 power." +
                "~#[[Ener Charge 2]], then add up to one target SIGNI in your Ener Zone to your hand."
        );
        
        setName("en_fan", "Garm, Verdant Angel");
        setDescription("en_fan",
                "@C: As long as there are 3 or more <<Angel>> SIGNI in your ener zone, this SIGNI gets +4000 power." +
                "~#[[Ener Charge 2]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand."
        );
        
		setName("zh_simplified", "翠天 嘉尔姆");
        setDescription("zh_simplified", 
                "@C :你的能量区的<<天使>>精灵在3张以上时，这只精灵的力量+4000。" +
                "~#[[能量填充2]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            return new TargetFilter().own().SIGNI().fromEner().withClass(CardSIGNIClass.ANGEL).getValidTargetsCount() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(2);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_CodeRideTruckMixer extends Card {
    
    public SIGNI_R1_CodeRideTruckMixer()
    {
        setImageSets("WXDi-P03-057");
        
        setOriginalName("コードライド　トラックミキサ");
        setAltNames("コードライドトラックミキサ Koodo Raido Torakkumikisa");
        setDescription("jp",
                "@A #D：このシグニをあなたの他の赤のシグニ１体の下に置く。\n\n" +
                "@C：このカードの上にある赤のシグニのパワーを＋2000する。" +
                "~#：対戦相手のパワー12000以下のシグニ１体を対象とし、%R %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Truck Mixer, Code: Ride");
        setDescription("en",
                "@A #D: Put this SIGNI under another red SIGNI on your field.\n\n" +
                "@C: The red SIGNI on top of this card gets +2000 power." +
                "~#You may pay %R %X. If you do, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Code Ride Truck Mixer");
        setDescription("en_fan",
                "@A #D: Put this SIGNI under 1 of your other red SIGNI.\n\n" +
                "@C: The red SIGNI on top of this card gets +2000 power." +
                "~#Target 1 of your opponent's SIGNI with power 12000 or less, and you may pay %R %X. If you do, banish it."
        );
        
		setName("zh_simplified", "骑乘代号 混凝土搅拌车");
        setDescription("zh_simplified", 
                "@A #D:这只精灵放置到你的其他的红色的精灵1只的下面。\n" +
                "@C :这张牌的上面的红色的精灵的力量+2000。" +
                "~#对战对手的力量12000以下的精灵1只作为对象，可以支付%R%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
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
            
            registerActionAbility(new DownCost(), this::onActionEff);
            
            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().withColor(CardColor.RED).over(cardId), new PowerModifier(2000));
            cont.setActiveUnderFlags(CardUnderCategory.UNDER);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onActionEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withColor(CardColor.RED).except(getCardIndex())).get();
            attach(cardIndex, getCardIndex(), CardUnderType.UNDER_GENERIC);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}

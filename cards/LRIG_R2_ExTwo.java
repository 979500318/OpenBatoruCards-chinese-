package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class LRIG_R2_ExTwo extends Card {
    
    public LRIG_R2_ExTwo()
    {
        setImageSets("WXDi-D07-003");
        
        setOriginalName("エクス・ツー");
        setAltNames("エクスツー Ekusu Tsuu");
        setDescription("jp",
                "@C：このカードが[[ソウル]]として付いているシグニは@>@U $T1：このシグニがアタックしたとき、対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。@@を得る。"
        );
        
        setName("en", "Ex Two");
        setDescription("en",
                "@C: The SIGNI with this card attached to it as a [[Soul]] gains@>@U $T1: When this SIGNI attacks, vanish target SIGNI on your opponent's field with power 12000 or less."
        );
        
        setName("en_fan", "Ex Two");
        setDescription("en_fan",
                "@C: The SIGNI attached with this card as a [[Soul]] gains:" +
                "@>@U $T1: When this SIGNI attacks, target 1 of your opponent's SIGNI with power 12000 or less, and banish it."
        );
        
		setName("zh_simplified", "艾克斯·贰");
        setDescription("zh_simplified", 
                "@C :被这张牌作为[[灵魂]]附加的精灵得到\n" +
                "@>@U $T1 :当这只精灵攻击时，对战对手的力量12000以下的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.EX);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));
        setLevel(2);
        setLimit(5);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().over(cardId), new AbilityGainModifier(this::onConstEffModGetSample));
            cont.setActiveLocation(CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT, CardLocation.CHEER);
            cont.setActiveUnderFlags(CardUnderType.ATTACHED_SOUL);
        }
        
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            AutoAbility attachedAuto = cardIndex.getIndexedInstance().registerAutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setUseLimit(UseLimit.TURN, 1);
            
            return attachedAuto;
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            banish(target);
        }
    }
}

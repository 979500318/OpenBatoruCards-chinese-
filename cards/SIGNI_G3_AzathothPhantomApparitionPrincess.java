package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;

public final class SIGNI_G3_AzathothPhantomApparitionPrincess extends Card {
    
    public SIGNI_G3_AzathothPhantomApparitionPrincess()
    {
        setImageSets("WXDi-P00-040");
        
        setOriginalName("幻怪姫　アザトース");
        setAltNames("ゲンカイキアザトース Genkaiki Azatoosu");
        setDescription("jp",
                "=T ＜アンシエント・サプライズ＞\n" +
                "^U：あなたのアタックフェイズ開始時、このシグニのパワーが１５０００以上の場合、対戦相手のパワー１００００以上のシグニ１体を対象とし、%G %Gを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@U：このシグニがアタックしたとき、あなたのトラッシュにあるシグニが持つクラスが７種類以上の場合、[[エナチャージ１]]をする。"
        );
        
        setName("en", "Azathoth, Phantom Spirit Queen");
        setDescription("en",
                "=T <<Ancient Surprise>>\n" +
                "^U: At the beginning of your attack phase, if this SIGNI's power is 15000 or more, you may pay %G %G. If you do, vanish target SIGNI on your opponent's field with power 10000 or more.\n" +
                "@U: Whenever this SIGNI attacks, if the total number of SIGNI classes in your trash is seven or more, [[Ener Charge 1]]."
        );
        
        setName("en_fan", "Azathoth, Phantom Apparition Princess");
        setDescription("en_fan",
                "=T <<Ancient Surprise>>\n" +
                "^U: At the beginning of your attack phase, if this SIGNI's power is 15000 or more, target 1 of your opponent's SIGNI with power 10000 or more, and you may pay %G %G. If you do, banish it.\n" +
                "@U: Whenever this SIGNI attacks, if there are 7 or more different classes from among cards in your trash, [[Ener Charge 1]]."
        );
        
		setName("zh_simplified", "幻怪姬 阿撒托斯");
        setDescription("zh_simplified", 
                "=T<<アンシエント・サプライズ>>\n" +
                "^U:你的攻击阶段开始时，这只精灵的力量在15000以上的场合，对战对手的力量10000以上的精灵1只作为对象，可以支付%G %G。这样做的场合，将其破坏。\n" +
                "@U :当这只精灵攻击时，你的废弃区的精灵持有类别合计7种类以上的场合，[[能量填充1]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private ConditionState onAutoEff1Cond()
        {
            return isLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE) &&
                   isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getCardIndex().getIndexedInstance().getPower().getValue() >= 15000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, 2)))
                {
                    banish(target);
                }
            }
        }
        
        private void onAutoEff2()
        {
            if(CardAbilities.getSIGNIClasses(getCardsInTrash(getOwner())).size() >= 7)
            {
                enerCharge(1);
            }
        }
    }
}

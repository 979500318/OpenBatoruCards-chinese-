package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R2_TamaMemoriaExplosiveGun extends Card {

    public SIGNI_R2_TamaMemoriaExplosiveGun()
    {
        setImageSets("WXDi-P11-060", "WXDi-P11-060P");

        setOriginalName("爆砲　タマ//メモリア");
        setAltNames("バクホウタマメモリア Bakuhou Tama Memoria");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニの左か右にダウン状態の＜ウェポン＞のシグニがある場合、対戦相手のパワー5000以下のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@U：このシグニがアタックしたとき、このシグニの左か右にダウン状態の＜アーム＞のシグニがある場合、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード１枚を対象とし、%Xを支払ってもよい。そうした場合、それをトラッシュに置く。"
        );

        setName("en", "Tama//Memoria, Erupting Cannon");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is a downed <<Weapon>> SIGNI to its left or right, you may pay %X. If you do, vanish target SIGNI on your opponent's field with power 5000 or less.\n" +
                "@U: Whenever this SIGNI attacks, if there is a downed <<Armed>> SIGNI to its left or right, you may pay %X. If you do, put target card from your opponent's Ener Zone that does not share a color with your opponent's Center LRIG into their trash."
        );
        
        setName("en_fan", "Tama//Memoria, Explosive Gun");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is a downed <<Weapon>> SIGNI to the left or right of this SIGNI, target 1 of your opponent's SIGNI with power 5000 or less, and you may pay %X. If you do, banish it.\n" +
                "@U: Whenever this SIGNI attacks, if there is a downed <<Arm>> SIGNI to the left or right of this SIGNI, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and you may pay %X. If you do, put it into the trash."
        );

		setName("zh_simplified", "爆炮 小玉//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的左侧或右侧有横置状态的<<ウェポン>>精灵的场合，对战对手的力量5000以下的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n" +
                "@U :当这只精灵攻击时，这只精灵的左侧或右侧有横置状态的<<アーム>>精灵的场合，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，可以支付%X。这样做的场合，将其放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private void onAutoEff1()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.WEAPON).downed().or(new TargetFilter().left(), new TargetFilter().right()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                
                if(target != null && payEner(Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
        
        private void onAutoEff2()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.ARM).downed().or(new TargetFilter().left(), new TargetFilter().right()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();

                if(target != null && payEner(Cost.colorless(1)))
                {
                    trash(target);
                }
            }
        }
    }
}

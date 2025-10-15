package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_W2_AltoFessoneExplosiveGun extends Card {

    public SIGNI_W2_AltoFessoneExplosiveGun()
    {
        setImageSets("WXDi-P14-052");

        setOriginalName("爆砲　アルト//フェゾーネ");
        setAltNames("バクホウアルトフェゾーネ Bakuhou Aruto Fezoone");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、そのアタックがこのターン三度目の場合、対戦相手のレベル１のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それを手札に戻す。"
        );

        setName("en", "Alto//Fesonne, Erupting Cannon");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if that is the third attack this turn, you may pay %X. If you do, return target level one SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Alto//Fessone, Explosive Gun");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if it is the third attack this turn, target 1 of your opponent's level 1 SIGNI, and you may pay %X. If you do, return it to their hand."
        );

		setName("zh_simplified", "爆炮 阿尔特//音乐节");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，那次攻击是这个回合第三次的场合，对战对手的等级1的精灵1只作为对象，可以支付%X。这样做的场合，将其返回手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
        }

        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && isOwnCard(event.getCaller())) == 3)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(1)).get();
                
                if(target != null && payEner(Cost.colorless(1)))
                {
                    addToHand(target);
                }
            }
        }
    }
}

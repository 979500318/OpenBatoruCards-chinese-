package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_G2_AiyaiFessoneSecondPlay extends Card {

    public SIGNI_G2_AiyaiFessoneSecondPlay()
    {
        setImageSets("WXDi-P14-064");

        setOriginalName("弍ノ遊　アイヤイ//フェゾーネ");
        setAltNames("ニノユウアイヤイフェゾーネ Ni no Yuu Aiyai Fezoone");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニのパワー以下の対戦相手のシグニ１体を対象とし、それとこのシグニをエナゾーンに置いてもよい。"
        );

        setName("en", "Aiyai//Fesonne, Second Play");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put this SIGNI and target SIGNI on your opponent's field with power less than or equal to this SIGNI into their owner's Ener Zone. "
        );
        
        setName("en_fan", "Aiyai//Fessone, Second Play");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power equal to or less than this SIGNI's, and you may put it and this SIGNI into the ener zone."
        );

		setName("zh_simplified", "贰之游 艾娅伊//音乐节");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这只精灵的力量以下的对战对手的精灵1只作为对象，可以将其和这只精灵放置到能量区。（这只精灵放置到能量区的场合，这只精灵没有战斗，不会给予玩家伤害）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(2);
        setPower(8000);

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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,getPower().getValue())).get();
            
            if(target != null && getCardIndex().isSIGNIOnField() && playerChoiceActivate())
            {
                putInEner(target);
                putInEner(getCardIndex());
            }
        }
    }
}

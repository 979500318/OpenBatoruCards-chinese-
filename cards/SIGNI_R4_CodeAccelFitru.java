package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R4_CodeAccelFitru extends Card {

    public SIGNI_R4_CodeAccelFitru()
    {
        setImageSets("WX22-020");

        setOriginalName("コードアクセル　ファイトラ");
        setAltNames("コードアクセルファイトラ Koodo Akuseru Faitora");
        setDescription("jp",
                "@U：このシグニがドライブ状態になったとき、対戦相手のシグニ１体を対象とし、%R %Rを支払ってもよい。そうした場合、それをバニッシュする。\n" +
                "@U：このシグニがアタックしたとき、対戦相手のパワー7000以下のシグニ１体を対象とし、それをバニッシュする。このシグニがドライブ状態の場合、代わりに対戦相手のパワー12000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1カードを１枚引く。\n" +
                "$$2対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Code Accel Fitru");
        setDescription("en",
                "@U: When this SIGNI enters the drive state, target 1 of your opponent's SIGNI, and you may pay %R %R. If you do, banish it.\n" +
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI with power 7000 or less, and banish it. If this SIGNI is in the drive state, instead target 1 of your opponent's SIGNI with power 12000 or less, and banish it." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card.\n" +
                "$$2 Target 1 of your opponent's SIGNI with power 10000 or less, and banish it."
        );

		setName("zh_simplified", "加速代号 火拼拖拉机");
        setDescription("zh_simplified", 
                "@U :当这只精灵变为驾驶状态时，对战对手的精灵1只作为对象，可以支付%R %R。这样做的场合，将其破坏。\n" +
                "@U :当这只精灵攻击时，对战对手的力量7000以下的精灵1只作为对象，将其破坏。这只精灵在驾驶状态的场合，作为替代，对战对手的力量12000以下的精灵1只作为对象，将其破坏。" +
                "~#以下选1种。\n" +
                "$$1 抽1张牌。\n" +
                "$$2 对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClass.RIDING_MACHINE);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.DRIVE, this::onAutoEff1);
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.RED, 2)))
            {
                banish(target);
            }
        }
        
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, !isState(CardStateFlag.IN_DRIVE) ? 7000 : 12000)).get();
            banish(target);
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                draw(1);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
                banish(target);
            }
        }
    }
}


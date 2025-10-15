package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_AtlasDissonaPhantomInsect extends Card {

    public SIGNI_R3_AtlasDissonaPhantomInsect()
    {
        setImageSets("WXDi-P13-067");

        setOriginalName("幻蟲　アトラス//ディソナ");
        setAltNames("ゲンチュウアトラスディソナ Genchuu Atorasu Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の２つから１つを選ぶ。\n" +
                "$$1あなたの場にあるすべてのシグニが#Sの場合、対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2対戦相手の場に、シグニに付いているカードかシグニの下に置かれているカードがある場合、対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Atlas//Dissona, Phantom Insect");
        setDescription("en",
                "@U: At the beginning of your attack phase, choose one of the following.\n$$1If all the SIGNI on your field are #S, vanish target SIGNI on your opponent's field with power 5000 or less.\n$$2If there is a card underneath or attached to a SIGNI on your opponent's field, vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Atlas//Dissona, Phantom Insect");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If all of your SIGNI are #S @[Dissona]@ SIGNI, target 1 of your opponent's SIGNI with power 5000 or less, and banish it.\n" +
                "$$2 If there is a SIGNI with a card attached to it or a SIGNI with a card under it on your opponent's field, target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "幻虫 南洋大兜虫//失调");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的2种选1种。\n" +
                "$$1 你的场上的全部的精灵是#S的场合，对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n" +
                "$$2 对战对手的场上有，精灵附加的牌或精灵的下面放置的牌的场合，对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.MISFORTUNE_INSECT);
        setLevel(3);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                if(new TargetFilter().own().SIGNI().not(new TargetFilter().dissona()).getValidTargetsCount() == 0)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                    banish(target);
                }
            } else if(new TargetFilter().OP().SIGNI().withCardsUnder(CardUnderCategory.UNDER.getFlags() | CardUnderCategory.ATTACHED.getFlags()).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
                banish(target);
            }
        }
    }
}


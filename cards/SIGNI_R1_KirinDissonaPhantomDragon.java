package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R1_KirinDissonaPhantomDragon extends Card {

    public SIGNI_R1_KirinDissonaPhantomDragon()
    {
        setImageSets("WXDi-P13-064");

        setOriginalName("幻竜　キリン//ディソナ");
        setAltNames("ゲンリュウキリンディソナ Genryuu Kirin Disona");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたの場に他の#Sのシグニがあり対戦相手のエナゾーンにカードが４枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。"
        );

        setName("en", "Qilin//Dissona, Phantom Dragon");
        setDescription("en",
                "@U: At the end of your turn, if there is another #S SIGNI on your field and there are four or more cards in your opponent's Ener Zone, your opponent chooses a card from their Ener Zone and puts it into their trash."
        );
        
        setName("en_fan", "Kirin//Dissona, Phantom Dragon");
        setDescription("en_fan",
                "@U: At the end of your turn, if there is another #S @[Dissona]@ SIGNI on your field and there are 4 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash."
        );

		setName("zh_simplified", "幻龙 麒麟//失调");
        setDescription("zh_simplified", 
                "@U 你的回合结束时，你的场上有其他的#S的精灵且对战对手的能量区的牌在4张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().dissona().except(getCardIndex()).getValidTargetsCount() > 0 &&
               getEnerCount(getOpponent()) >= 4)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
    }
}


package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K3_FurfurWickedDevil extends Card {

    public SIGNI_K3_FurfurWickedDevil()
    {
        setImageSets("WXDi-P11-082");

        setOriginalName("凶魔　フルフル");
        setAltNames("キョウマフルフル Kyouma Furufuru");
        setDescription("jp",
                "@C：あなたのトラッシュにカードが１５枚以上あるかぎり、このシグニのパワーは＋3000される。\n" +
                "@U：あなたのターン終了時、あなたのデッキの上からカードを６枚トラッシュに置く。この方法でカードを６枚トラッシュに置いた場合、対戦相手のデッキの上からカードを６枚トラッシュに置く。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Furufuru, Doomed Evil");
        setDescription("en",
                "@C: As long as you have fifteen or more cards in your trash, this SIGNI gets +3000 power.\n" +
                "@U: At the end of your turn, put the top six cards of your deck into your trash. If exactly six cards were put into your trash this way, put the top six cards of your opponent's deck into their trash." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Furfur, Wicked Devil");
        setDescription("en_fan",
                "@C: As long as there are 15 or more cards in your trash, this SIGNI gets +3000 power.\n" +
                "@U: At the end of your turn, put the top 6 cards of your deck into the trash. If 6 cards were put into the trash this way, put the top 6 cards of your opponent's deck into the trash." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "凶魔 弗法");
        setDescription("zh_simplified", 
                "@C :你的废弃区的牌在15张以上时，这只精灵的力量+3000。\n" +
                "@U :你的回合结束时，从你的牌组上面把6张牌放置到废弃区。这个方法把6张牌放置到废弃区的场合，从对战对手的牌组上面把6张牌放置到废弃区。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getTrashCount(getOwner()) >= 15 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            int count = millDeck(6).size();
            if(count == 6) millDeck(getOpponent(), 6);
        }

        private void onEnterEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withLevel(3,0).fromHand()).get();
            attach(getCardIndex(), cardIndex, CardUnderType.UNDER_GENERIC);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
            {
                addToHand(target);
            } else {
                putOnField(target);
            }
        }
    }
}


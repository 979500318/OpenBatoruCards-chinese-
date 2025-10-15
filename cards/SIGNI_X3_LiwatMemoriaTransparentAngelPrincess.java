package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_X3_LiwatMemoriaTransparentAngelPrincess extends Card {

    public SIGNI_X3_LiwatMemoriaTransparentAngelPrincess()
    {
        setImageSets("WXDi-P07-049", "WXDi-P07-049P");

        setOriginalName("透天姫　リワト//メモリア");
        setAltNames("トウテンキリワトメモリア Toutenki Riwato Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の３つから１つを選ぶ。\n" +
                "$$1カードを１枚引くか【エナチャージ１】をする。\n" +
                "$$2対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$3対戦相手のシグニ１体を対象とし、%X %X %Xを支払ってもよい。そうした場合、それを手札に戻す。\n" +
                "@E：あなたの公開領域に＜天使＞ではない、色を持つ表向きのシグニであるカードがある場合、このシグニを場からトラッシュに置く。"
        );

        setName("en", "Liwat//Memoria, Lucent Angel Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, choose one of the following.\n" +
                "$$1 Draw a card or [[Ener Charge 1]].\n" +
                "$$2 Vanish target level one SIGNI on your opponent's field.\n" +
                "$$3 You may pay %X %X %X. If you do, return target SIGNI on your opponent's field to its owner's hand.\n" +
                "@E: If there is a face-up card with a color that is a SIGNI and not an <<Angel>> in your Public Zone, put this SIGNI on your field into its owner's trash."
        );
        
        setName("en_fan", "Liwat//Memoria, Transparent Angel Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Draw 1 card or [[Ener Charge 1]].\n" +
                "$$2 Target 1 of your opponent's level 1 SIGNI, and banish it.\n" +
                "$$3 Target 1 of your opponent's SIGNI, and you may pay %X %X %X. If you do, return it to their hand.\n" +
                "@E: If there is a face-up non-<<Angel>> colored SIGNI in one of your public zones, put this SIGNI from the field into the trash."
        );

		setName("zh_simplified", "透天姬 莉瓦托 //回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的3种选1种。\n" +
                "$$1 抽1张牌或[[能量填充1]]。\n" +
                "$$2 对战对手的等级1的精灵1只作为对象，将其破坏。\n" +
                "$$3 对战对手的精灵1只作为对象，可以支付%X %X %X。这样做的场合，将其返回手牌。\n" +
                "@E :你的公开领域有不是<<天使>>，持有颜色的表向的精灵的牌的场合，这只精灵从场上放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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

            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            switch(playerChoiceMode())
            {
                case 1 -> {
                    if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
                    {
                        draw(1);
                    } else {
                        enerCharge(1);
                    }
                }
                case 1<<1 -> {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
                    banish(target);
                }
                case 1<<2 -> {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
                    
                    if(target != null && payEner(Cost.colorless(3)))
                    {
                        addToHand(target);
                    }
                }
            }
        }

        private void onEnterEff()
        {
            if(CardLocation.isSIGNI(getCardIndex().getLocation()) &&
               new TargetFilter().own().SIGNI().withColor().not(new TargetFilter().withClass(CardConst.CardSIGNIClass.ANGEL)).faceUp().publicLocation().getValidTargetsCount() > 0)
            {
                trash(getCardIndex());
            }
        }
    }
}

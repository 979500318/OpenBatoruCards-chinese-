package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;

public final class SIGNI_R3_BurningChorizoGreatTrap extends Card {

    public SIGNI_R3_BurningChorizoGreatTrap()
    {
        setImageSets("WX24-P3-050");
        setLinkedImageSets("WX24-P3-018");

        setOriginalName("大罠　バーニングチョリソー");
        setAltNames("ダイビンバーニングチョリソー Daibin Baaningu Chorisoo");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に《ちより　第三章》がいる場合、このシグニと同じシグニゾーンにある【マジックボックス】１つを表向きにしトラッシュに置いてもよい。その後、そのカードが##を持つ場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。##を持たない場合、このアタックを無効にし、対戦相手が%X %X %X %X %Xを支払わないかぎり、対戦相手にダメージを与える。\n" +
                "@E：場に他の＜トリック＞のシグニがない場合、このシグニをダウンする。"
        );

        setName("en", "Burning Chorizo, Great Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if your LRIG is \"Chiyori, Chapter Three\", you may put 1 [[Magic Box]] in the same SIGNI zone as this SIGNI face-up into the trash. Then, if that card has a ## @[Life Burst]@, target 1 of your opponent's SIGNI, and banish it. If it doesn't have a ## @[Life Burst]@, disable that attack, and damage your opponent unless they pay %X %X %X %X %X.\n" +
                "@E: If there is no other <<Trick>> SIGNI on the field, down this SIGNI."
        );

		setName("zh_simplified", "大罠 燃烧香肠");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的场上有《ちより　第三章》的场合，可以把与这只精灵相同精灵区的[[魔术箱]]1个表向并放置到废弃区。然后，那张牌持有##的场合，对战对手的精灵1只作为对象，将其破坏。不持有##的场合，这次攻击无效，如果对战对手不把%X %X %X %X %X:支付，那么给予对战对手伤害。\n" +
                "@E 场上没有其他的<<トリック>>精灵的场合，这只精灵#D。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(3);
        setPower(15000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("ちより　第三章"))
            {
                DataTable<CardIndex> data = new TargetFilter().own().withUnderType(CardUnderType.ZONE_MAGIC_BOX).fromSafeLocation(getCardIndex().getLocation()).getExportedData();
                if(!data.isEmpty() && playerChoiceActivate() && trash(data) > 0)
                {
                    data.get().getIndexedInstance().findLifeBurstAbility().ifPresentOrElse(ability -> {
                        CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                        banish(target);
                    }, () -> {
                        disableNextAttack(getCardIndex());
                        
                        if(!payEner(getOpponent(), Cost.colorless(5)))
                        {
                            damage(getOpponent());
                        }
                    });
                }
            }
        }

        private void onEnterEff()
        {
            if(new TargetFilter().SIGNI().withClass(CardSIGNIClass.TRICK).except(getCardIndex()).getValidTargetsCount() == 0)
            {
                down();
            }
        }
    }
}

package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_K1_Code2434ChaikaHanabatake extends Card {

    public SIGNI_K1_Code2434ChaikaHanabatake()
    {
        setImageSets("WXDi-CP01-042");

        setOriginalName("コード２４３４　花畑チャイカ");
        setAltNames("コードニジサンジハナバタケチャイカ Koodo Nijisanji Hanabatake Chaika");
        setDescription("jp",
                "@E：あなたのデッキの一番上のカードをトラッシュに置く。その後、あなたのトラッシュに＜バーチャル＞のシグニが１０枚以上ある場合、%X %Xを支払ってもよい。そうした場合、あなたのトラッシュから＜バーチャル＞のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Hanabatake Chaika, Code 2434");
        setDescription("en",
                "@E: Put the top card of your deck into your trash. Then, if there are ten or more <<Virtual>> SIGNI in your trash, you may pay %X %X. If you do, add target <<Virtual>> SIGNI from your trash to your hand." +
                "~#Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Code 2434 Chaika Hanabatake");
        setDescription("en_fan",
                "@E: Put the top card of your deck into the trash. Then, if there are 10 or more <<Virtual>> SIGNI in your trash, target 1 <<Virtual>> SIGNI from your trash, and you may pay %X %X. If you do, add it to your hand." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "2434代号 花畑嘉依卡");
        setDescription("zh_simplified", 
                "@E :你的牌组最上面的牌放置到废弃区。然后，你的废弃区的<<バーチャル>>精灵在10张以上的场合，可以支付%X %X。这样做的场合，从你的废弃区把<<バーチャル>>精灵1张作为对象，将其加入手牌。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onEnterEff()
        {
            millDeck(1);
            
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash().getValidTargetsCount() >= 10)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash()).get();
                
                if(target != null && payEner(Cost.colorless(2)))
                {
                    addToHand(target);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}

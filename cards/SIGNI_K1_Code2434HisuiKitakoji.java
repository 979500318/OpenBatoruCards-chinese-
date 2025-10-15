package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K1_Code2434HisuiKitakoji extends Card {

    public SIGNI_K1_Code2434HisuiKitakoji()
    {
        setImageSets("WXDi-CP01-044");

        setOriginalName("コード２４３４　北小路ヒスイ");
        setAltNames("コードニジサンジキタコウジヒスイ Koodo Nijisanji Kitakouji Hisui");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に他の＜バーチャル＞のシグニがある場合、対戦相手のデッキの上からカードを２枚トラッシュに置く。\n" +
                "@E %X：あなたのトラッシュから＜世怜音女学院＞のシグニ１枚を対象とし、それをエナゾーンに置く。"
        );

        setName("en", "Kitakoji Hisui, Code 2434");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is another <<Virtual>> SIGNI on your field, put the top two cards of your opponent's deck into their trash.\n@E %X: Put target <<SELENE Girls' Academy>> SIGNI from your trash into your Ener Zone."
        );
        
        setName("en_fan", "Code 2434 Hisui Kitakoji");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is another <<Virtual>> SIGNI on your field, put the top 2 cards of your opponent's deck into the trash.\n" +
                "@E %X: Target 1 <<Selene Girls' Academy>> SIGNI from your trash, and put it into the ener zone."
        );

		setName("zh_simplified", "2434代号 北小路翡翠");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，你的场上有其他的<<バーチャル>>精灵的场合，从对战对手的牌组上面把2张牌放置到废弃区。\n" +
                "@E %X:从你的废弃区把<<世怜音女学院>>精灵1张作为对象，将其放置到能量区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL, CardSIGNIClass.SELENE_GIRLS_ACADEMY);
        setLevel(1);
        setPower(2000);

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

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                millDeck(getOpponent(), 2);
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).own().SIGNI().withClass(CardSIGNIClass.SELENE_GIRLS_ACADEMY).fromTrash()).get();
            putInEner(target);
        }
    }
}
